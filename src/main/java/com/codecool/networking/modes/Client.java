package com.codecool.networking.modes;

import com.codecool.networking.tasks.client.ClientFlowController;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private String hostName;
    private int port;

    public Client(int port, String hostName){
        this.port = port;
        this.hostName = hostName;
    }

    public void start() {
        System.out.println("Launching Client");

        try(Socket socket = new Socket(hostName, port)){
            new ClientFlowController(socket);
            System.out.println("Client Launched");
            while (true){}
        }catch (IOException e){
            System.out.println("Could not find host");
            System.exit(0);
        }
    }
}
