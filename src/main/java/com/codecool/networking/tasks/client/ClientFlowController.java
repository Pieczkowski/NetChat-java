package com.codecool.networking.tasks.client;

import com.codecool.networking.data.Message;
import com.codecool.networking.data.TransferObject;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientFlowController {

    private volatile boolean clientRegistered;
    private volatile boolean waitingForServerResponse;
    private String receiver;
    private String tempReceiver;
    private ClientWriter writer;

    public ClientFlowController(Socket socket){
        clientRegistered = false;
        waitingForServerResponse = false;
        ClientListener listener = new ClientListener(socket, this);
        writer = new ClientWriter(socket, this);
        new Thread(writer).start();
        new Thread(listener).start();
    }

    synchronized String getReceiver() {
        return receiver;
    }

    synchronized String registerAndGetName(Scanner scanner) throws IOException, InterruptedException {
        String name = "";
        while (!clientRegistered) {
                System.out.println("Insert your name:");
                name = scanner.nextLine();
                sendRegistrationRequest(name);
        }
        System.out.println("You have been registered");
        return name;
    }

    private synchronized void sendRegistrationRequest(String name) throws IOException, InterruptedException{
        TransferObject transferObject;
        transferObject = new Message.MessageBuilder("/register", name).setAuthor("Not Registered").build();
        writer.send(transferObject);
        wait();
    }

    void changeReceiver(String name, Scanner scanner) throws IOException, InterruptedException{
        receiver = null;
        while (receiver == null) {
            requestUserListFromServer(name);
            sendReceiverRequest(name, scanner);
        }
    }

    private synchronized void requestUserListFromServer(String name) throws IOException, InterruptedException{
        writer.send(new Message.MessageBuilder("/set", name).setAuthor(name).build());
        waitingForServerResponse = true;
        wait();
    }

    private void sendReceiverRequest(String name, Scanner scanner) throws IOException, InterruptedException{
        System.out.println("Select receiver");
        tempReceiver = scanner.nextLine();
        synchronized (this) {
            writer.send(new Message.MessageBuilder(tempReceiver, name).setAuthor(name).build());
            wait();
        }
    }

    void evaluateInput(TransferObject input) {
        if(input.getAuthor().equals("SERVER")){
            processServerMessage(input);
        }else{
            System.out.println(input);
        }
    }

    private synchronized void processServerMessage(TransferObject input) {
        String content = input.getContent().toString();
        if (!clientRegistered) {
            processServerResponseForRegistration(input);
        }else if (waitingForServerResponse){
            System.out.printf("Available users: %s\n", content);
            waitingForServerResponse = false;
            notify();
        } else{
            setReceiver(input);
        }
    }

    private synchronized void processServerResponseForRegistration(TransferObject input) {
        if (Boolean.valueOf(input.getContent().toString())) {
            clientRegistered = true;
        }
        notify();
    }

    private synchronized void setReceiver(TransferObject input){
        if (Boolean.valueOf(input.getContent().toString())) {
            receiver = tempReceiver;
            System.out.println("Receiver has been set");
        } else {
            System.out.println("It is not valid receiver");
        }
        waitingForServerResponse = false;
        notify();
    }
}
