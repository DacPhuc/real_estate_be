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
        String filtered = message.replaceAll("[^0-9,]","");
        String[] coordinate = filtered.split(",");
        HashMap<String, String> result = new HashMap<>();
        result.put("lat", coordinate[1]);
        result.put("lng", coordinate[0]);
        websocket.convertAndSend("/topic/dacphuc", result);
    }

    public void sendComment(int id){
        websocket.convertAndSend("/topic/comment", id);
    }
}
