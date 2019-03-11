package com.codecool.networking.tasks.server;

import com.codecool.networking.data.TransferObject;

import java.io.IOException;
import java.io.ObjectOutputStream;

class MessagePasser {

    private ObjectOutputStream out;

    MessagePasser(ObjectOutputStream out){
        this.out = out;
    }

    void passMessage(TransferObject transferObject){
        try {
            out.writeObject(transferObject);
        } catch (IOException e) {
            System.out.println("Failed to send message.");
            e.printStackTrace();
        }
    }
}
