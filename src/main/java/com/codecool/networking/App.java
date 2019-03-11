package com.codecool.networking;

import com.codecool.networking.modes.Client;
import com.codecool.networking.modes.Server;

public class App {

    private static int port = 8001;
    private static String hostName = "localhost";

    public static void main(String[] args) {

        switch (args.length){
            case 0:
                System.out.println("Please specify which mode would you like to launch. (client/server)");
                break;
            case 1:
                System.out.println("Please specify port.");
                break;
            default:
                String mode = args[0];
                try {
                    port = Integer.parseInt(args[1]);
                    startMode(mode);
                } catch (NumberFormatException e){
                    System.out.println("Please specify valid port.");
                }
                break;
        }
    }

    private static void startMode(String selectedMode){
        if (isArgumentClient(selectedMode)){
            Client client = new Client(port, hostName);
            client.start();
        } else if (isArgumentServer(selectedMode)){
            Server server = new Server(port);
            server.start();
        } else {
            System.out.println("This is not valid mode.");
        }
    }

    private static boolean isArgumentClient(String arg){
        return arg.equalsIgnoreCase("client");
    }

    private static boolean isArgumentServer(String arg){
        return arg.equalsIgnoreCase("server");
    }
}
