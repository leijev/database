package com.example.springjwt.ws;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

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
    private final Map<String, String> currentMembers = new HashMap<>();

    private Logger logger = Logger.getAnonymousLogger();

    @MessageMapping("/chat")
    public void sendMessageToChat(Message message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        String username = (String) simpMessageHeaderAccessor.getSessionAttributes().get("username");

        if (username == null) {
            username = message.getUsername();
            simpMessageHeaderAccessor.getSessionAttributes().put("username", username);
        }

        String hashId = simpMessageHeaderAccessor.getSessionId();
        Message sendMessage = new Message(username, message.getMessage());

        if (checkIdentity(hashId, username) || currentMembers.size() < 1) {
            currentMembers.put(hashId, username);
            simpMessagingTemplate.convertAndSend("/topic/chat", sendMessage.generateMessage());
            return;
        }

        simpMessagingTemplate.convertAndSend("/topic/chat", currentMembers.get(hashId) + ": " + message.getMessage());
        logger.info("new message received: " + currentMembers.get(hashId));
    }

    public boolean checkIdentity(String hashId, String username) {
        return currentMembers.get(hashId) == username ? false : true;
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        logger.info("session disconnect event");

        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        Map<String, Object> sessionAttributes = stompHeaderAccessor.getSessionAttributes();

        String username = (String) sessionAttributes.get("username");
        if (sessionAttributes == null || username == null) {
            return;
        }

        Message message = new Message("Server", username + " disconnect from room");
        simpMessagingTemplate.convertAndSend("/topic/chat", message.generateMessage());
    }

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent sessionConnectEvent) {
        simpMessagingTemplate.convertAndSend("/topic/chat", "New client connected");
    }
}
