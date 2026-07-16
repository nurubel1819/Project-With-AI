package com.example.Project_With_AI.web_socket.controllers;


import com.example.Project_With_AI.web_socket.dtos.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/send")        // client পাঠাবে "/app/send"-এ
    @SendTo("/topic/messages")      // যারা "/topic/messages" subscribe করেছে তারা পাবে
    public ChatMessage broadcast(ChatMessage message) {
        return message; // চাইলে এখানে processing/validation/DB save করা যায়
    }
}
