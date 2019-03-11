package com.codecool.networking.data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable, TransferObject {
    private String content;
    private String author;
    private String receiver;
    private LocalDateTime createdAt;

    private Message(MessageBuilder messageBuilder) {
        this.content = messageBuilder.content;
        this.author = messageBuilder.author;
        this.receiver = messageBuilder.receiver;
        this.createdAt = LocalDateTime.now();
    }

    @Override
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

    public static class MessageBuilder{
        private String content;
        private String author = "SERVER";
        private String receiver;

        public MessageBuilder(String content, String receiver){
            this.content = content;
            this.receiver = receiver;
        }

        public MessageBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Message build(){
            return new Message(this);
        }
    }
}
