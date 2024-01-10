package com.example.springjwt.ws.rooms;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;

@Data
@RequiredArgsConstructor
public class Room {
    private final String chatId;
    private HashSet<String> users = new HashSet<>();

    public void addUser(String username) {
        users.add(username);

        //log
        System.out.println("added " + username);
    }

    public void remove(String username) {
        users.remove(username);

        //log
        System.out.println("deleted " + username);
    }
}
