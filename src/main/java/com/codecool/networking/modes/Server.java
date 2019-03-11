package com.codecool.networking.modes;

import com.codecool.networking.tasks.server.ServerFlowController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Server {

    private static ConcurrentMap<String, ServerFlowController> usersControllers;
    private int port;

    public Server(int port){
        this.port = port;
        usersControllers = new ConcurrentHashMap<>();
    }

    public void start() {
        System.out.println("Launched Server");
        boolean running = true;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (running) {
                Socket clientSocket = serverSocket.accept();
                new ServerFlowController(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Failed to launch server.");
        }
    }

    public static void handleUserExit(){
        //TODO
    }

    public static boolean isUserRegistered(String name){
        return usersControllers.containsKey(name);
    }

    public synchronized static boolean tryToRegister(String name, ServerFlowController flowController){
        if (usersControllers.containsKey(name)){
            return false;
        } else {
            register(name, flowController);
            System.out.println("Registered " + name);
            return true;
        }
    }

    private static void register(String name, ServerFlowController flowController){
        usersControllers.put(name, flowController);
    }

    public static Set<String> getUsers(){
        return usersControllers.keySet();
    }

    public static ServerFlowController getFlowController(String name){
        return usersControllers.get(name);
    }

    public static int getHowManyUsersAreRegistered(){
        return usersControllers.size();
    }
}