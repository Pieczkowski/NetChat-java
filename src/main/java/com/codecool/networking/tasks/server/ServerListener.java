package com.codecool.networking.tasks.server;

import com.codecool.networking.data.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerListener implements Runnable {

    private ServerFlowController serverFlowController;
    private Thread listenerThread;
    private ObjectInputStream inputStream;

    public ServerListener(ObjectInputStream inputStream, ServerFlowController serverFlowController){
        this.serverFlowController = serverFlowController;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        listenerThread = Thread.currentThread();
        try {
            Message input;
            while (!listenerThread.isInterrupted()) {
                if ((input = (Message) inputStream.readObject()) != null) {
                    serverFlowController.handleInput(input);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}