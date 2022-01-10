package com.reins.bookstore.websocket.messages;

/* Represents a join message for the chat */
public class JoinMessage extends Message {
    private String name;

    public JoinMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /* For logging purposes */
    @Override
    public String toString() {
        return "[JoinMessage] " + name;
    }
}