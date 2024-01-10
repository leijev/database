package com.example.springjwt.ws.rooms;

import lombok.Data;

@Data
public class CreateRoomRequest {
    private String roomId;
    private String username;
}
