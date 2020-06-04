package com.project.se.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EstateSocketService {
    @Autowired
    private SimpMessagingTemplate websocket;

    public void sendMessageToTopic(String message){
        String[] coordinate = message.split(" ");
        HashMap<String, String> result = new HashMap<>();
        result.put("lat", coordinate[0]);
        result.put("lng", coordinate[1]);
        websocket.convertAndSend("/topic/dacphuc", result);
    }
}
