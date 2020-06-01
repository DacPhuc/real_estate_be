package com.project.se.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class EstateSocketService {
    @Autowired
    private SimpMessagingTemplate websocket;

    public void sendMessageToTopic(String topic, String message){
        websocket.convertAndSend("/topic/dacphuc", message);
    }
}
