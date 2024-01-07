package com.example.springjwt.ws;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private String username;
    private String message;

    public String generateMessage() {
        return String.format("%s : %s", username, message);
    }
}
