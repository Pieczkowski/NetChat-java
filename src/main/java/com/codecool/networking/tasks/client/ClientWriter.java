package com.codecool.networking.tasks.client;

import com.codecool.networking.data.Message;
import com.codecool.networking.data.TransferObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientWriter implements Runnable {
    private Socket socket;
    private ClientFlowController flowController;
    private String clientName;
    private ObjectOutputStream out;


    ClientWriter(Socket socket, ClientFlowController flowController){
        this.socket = socket;
        this.flowController = flowController;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            clientName = flowController.registerAndGetName(scanner);
            flowController.changeReceiver(clientName, scanner);

            String input;
            while (true) {
                if ((input = scanner.nextLine()) != null) {
                    if (input.equals("/set")){
                        flowController.changeReceiver(clientName, scanner);
                    } else {
                        Message message = new Message.MessageBuilder(input, flowController.getReceiver()).setAuthor(clientName).build();
                        send(message);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to send message in client writer.");
            e.printStackTrace();
        }
    }

    void send(TransferObject object) throws IOException{
        out.writeObject(object);
    }
}
