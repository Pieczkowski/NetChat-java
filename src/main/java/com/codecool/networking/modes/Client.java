package com.codecool.networking.modes;

import com.codecool.networking.tasks.client.ClientFlowController;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress ip;
    private int port;

    public Client(int port, InetAddress ip){
        this.port = port;
        this.ip = ip;
    }

    public void start() {
        System.out.println("Launching Client");

        try(Socket socket = new Socket(ip, port)){
            new ClientFlowController(socket);
            System.out.println("Client Launched");
            while (true){}
        }catch (IOException e){
            System.out.println("Could not find host");
            System.exit(0);
        }
    }
}
