package com.example.springjwt.ws.messages;

import lombok.Data;

@Data
public class Message {
    private String username;
    private String message;

    public String generateMessage() {
        return String.format("%s: %s", username, message);
    }
}
