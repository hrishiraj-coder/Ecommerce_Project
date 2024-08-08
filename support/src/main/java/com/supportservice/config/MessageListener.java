package com.supportservice.config;

import com.supportservice.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final SimpMessagingTemplate messagingTemplate;


    @EventListener
    public void handleWebSocketConnectionListener(SessionConnectedEvent event) {
        System.out.println("New Event Created");
    }


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = stompHeaderAccessor.getSessionAttributes().get("username").toString();

        if (username != null) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType("Leave");
            chatMessage.setSender(username);
            messagingTemplate.convertAndSend("/topic/public", chatMessage);

        }
    }

}
