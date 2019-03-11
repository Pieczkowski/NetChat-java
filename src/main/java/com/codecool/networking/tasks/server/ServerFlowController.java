package com.codecool.networking.tasks.server;

import com.codecool.networking.data.Message;
import com.codecool.networking.data.TransferObject;
import com.codecool.networking.modes.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerFlowController {

    private MessagePasser passer;
    private Thread listener;
    private boolean userSetsReceiver = false;

    public ServerFlowController(Socket clientSocket) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        passer = new MessagePasser(out);
        listener = new Thread(new ServerListener(new ObjectInputStream(clientSocket.getInputStream()), this));
        listener.start();
    }

    void handleInput(TransferObject input){
        String receiver = input.getReceiver();
        if (input instanceof Message){
            handleMessage(receiver, (Message) input);
        } else {
            // other types of transfer object are not supported right now
        }
    }

    private void handleMessage(String receiver, Message message) {
        if (userSetsReceiver){
            handleUserSelectsReceiver(message, receiver);
        }else {
            switch (message.getContent()) {
                case "/set":
                    userSetsReceiver = true;
                case "/clients":
                    sendRegisteredUsers(receiver);
                    break;
                case "/quit":
                    // TODO
                    break;
                case "/register":
                    handleRegisterRequest(receiver);
                    break;
                default :
                    passMessage(message.getAuthor(), receiver, message);
            }
        }
    }

    private void passMessage(String author, String receiver, Message message) {
        if (Server.isUserRegistered(receiver)){
            System.out.printf("Passing message from %s to %s\n", author, receiver);
            Server.getFlowController(receiver).sendMessage(message);
        } else
            sendMessage(new Message.MessageBuilder("This user could not be reached", author).build());
    }

    private void sendRegisteredUsers(String receiver) {
        System.out.printf("Sending user list to %s\n", receiver);
        TransferObject transferObject = new Message.MessageBuilder(Server.getUsers().toString(), receiver).build();
        sendMessage(transferObject);
    }

    private void sendBooleanInMessage(Boolean booleanVal, String receiver) {
        TransferObject transferObject = new Message.MessageBuilder(booleanVal.toString(), receiver).build();
        sendMessage(transferObject);
    }

    private void sendMessage(TransferObject transferObject) {
        passer.passMessage(transferObject);
    }

    private void handleRegisterRequest(String name){
        if (Server.isUserRegistered(name)){
            String content = "You are already registered";
            handleMessage(name, new Message.MessageBuilder(content, name).build());
        } else {
            registerUser(name);
        }
    }

    private void registerUser(String name){
        System.out.printf("Attempting to register %s\n", name);
        boolean registrationSuccessful = Server.tryToRegister(name, this);
        sendBooleanInMessage(registrationSuccessful, name);
    }

    private void handleUserSelectsReceiver(Message message, String user) {
        String selectedReceiver = message.getContent();
        boolean userSelectedSelfAsReceiver = message.getAuthor().equals(selectedReceiver);
        if (Server.isUserRegistered(selectedReceiver) && !userSelectedSelfAsReceiver) {
            sendBooleanInMessage(Boolean.TRUE, user);
        } else {
            sendBooleanInMessage(Boolean.FALSE, user);
        }
            userSetsReceiver = false;
    }
}
