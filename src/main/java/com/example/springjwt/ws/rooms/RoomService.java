package com.example.springjwt.ws.rooms;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Data
@Service
public class RoomService {
    private Map<String, Room> rooms = new HashMap<>();

    public Room getRoom(String chatId) {
        return rooms.get(chatId);
    }

    public Room createRoom(String roomId) {
        rooms.put(roomId, new Room(roomId));
        return rooms.get(roomId);
    }

    public void removeUserFromRoom(String chatId, String username) {
        rooms.get(chatId).getUsers().remove(username);
        System.out.println("removed " + username);
    }
}
