package com.codecool.networking;

import com.codecool.networking.modes.Client;
import com.codecool.networking.modes.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class App {

    private static int port = 8001;
    private static InetAddress address = InetAddress.getLoopbackAddress();
    private static String mode;

    public static void main(String[] args) {
        switch (args.length){
            case 0:
                System.out.println("Please specify which mode would you like to launch. (client/server)");
                break;
            case 1:
                System.out.println("Please specify port.");
                break;
            default:
                System.out.println(address);
                extractParametersFromArgs(args);
                System.out.println(address);
                startMode(mode);
        }
    }

    private static void startMode(String selectedMode){
        if (isArgumentClient(selectedMode)){
            Client client = new Client(port, address);
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

    private static void extractParametersFromArgs(String[] args){
        setMode(args);
        setPort(args);
        if (args.length > 2) {
            setAddress(args);
        }
    }

    private static void setMode(String[] args) {
        int modeIndex = 0;
        mode = args[modeIndex];
    }

    private static void setPort(String[] args) {
        int portIndex = 1;
        try {
            port = Integer.parseInt(args[portIndex]);
        } catch (NumberFormatException e){
            System.out.println("Please specify valid port.");
            System.exit(0);
        }
    }

    private static void setAddress(String[] args) {
        int addressIndex = 2;
        try {
            address = InetAddress.getByName(args[addressIndex]);
        } catch (UnknownHostException e){
            System.out.println("Host address could not be determined");
            System.exit(0);
        }
    }

}
