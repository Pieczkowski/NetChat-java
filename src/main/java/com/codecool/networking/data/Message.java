package com.codecool.networking.data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String content;
    private String author;
    private String receiver;
    private LocalDateTime createdAt;

    public Message(String content, String author) {
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    @Override
    public String getReceiver() {
        return receiver;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
