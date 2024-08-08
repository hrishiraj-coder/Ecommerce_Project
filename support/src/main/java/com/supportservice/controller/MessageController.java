package com.supportservice.controller;

import com.supportservice.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class MessageController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chatbot")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/chatbot")
    public ChatMessage newUser(@Payload ChatMessage message, SimpMessageHeaderAccessor accessor){

        accessor.getSessionAttributes().put("username",message.getSender());
        return message;

    }
}
