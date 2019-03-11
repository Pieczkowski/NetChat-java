package com.codecool.networking.tasks.client;

import com.codecool.networking.data.TransferObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListener implements Runnable {

    private Socket clientSocket;
    private ClientFlowController flowController;

    ClientListener(Socket clientSocket, ClientFlowController flowController){
        this.clientSocket = clientSocket;
        this.flowController = flowController;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            TransferObject input;

            while (true) {
                if ((input = (TransferObject) in.readObject()) != null) {
                    flowController.evaluateInput(input);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Whoops");
            e.printStackTrace();
        }
    }
}