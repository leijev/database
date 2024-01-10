package com.example.springjwt.ws;

import com.example.springjwt.ws.messages.Message;
import com.example.springjwt.ws.rooms.CreateRoomRequest;
import com.example.springjwt.ws.rooms.Room;
import com.example.springjwt.ws.rooms.RoomService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Controller
public class WebsocketController
{
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RoomService roomService;

    @MessageMapping("/joinChat")
    public void joinChat(CreateRoomRequest createRoomRequest, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        String roomId = createRoomRequest.getRoomId();
        String username = createRoomRequest.getUsername();

        if (simpMessageHeaderAccessor.getSessionAttributes().get("currentRoomId") != null) {
            String currentRoomId = (String) simpMessageHeaderAccessor.getSessionAttributes().get("currentRoomId");
            roomService.removeUserFromRoom(currentRoomId, username);

            simpMessagingTemplate.convertAndSend(
                    "/topic/chat/" + currentRoomId, String.format("%s disconnected", username)
            );
        }

        if (roomService.getRoom(roomId) == null) {
            roomService.createRoom(roomId).addUser(username);
        } else {
            roomService.getRoom(roomId).addUser(username);
        }

        simpMessagingTemplate.convertAndSend(
                "/topic/chat/" + roomId, String.format("%s connected", username)
        );

        simpMessageHeaderAccessor.getSessionAttributes().put("currentRoomId", roomId);
    }

    @MessageMapping("/chat/{id}")
    public void sendMessage(@DestinationVariable("id") String roomId, @Payload Message message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        Room room = roomService.getRoom(roomId);
        if (room == null || !room.getUsers().contains(message.getUsername())) {
            return;
        }

        String currentRoomId = (String) simpMessageHeaderAccessor.getSessionAttributes().get("currentRoomId");
        if (currentRoomId.equals(roomId)) {
            simpMessagingTemplate.convertAndSend(
                    "/topic/chat/" + roomId, message.generateMessage()
            );
            return;
        }

        simpMessagingTemplate.convertAndSend("/topic/chat", "Connect to this room to send messages");
    }

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent sessionConnectEvent) {
        System.out.println("new session connect event");
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        System.out.println("session disconnect event");
    }

}
