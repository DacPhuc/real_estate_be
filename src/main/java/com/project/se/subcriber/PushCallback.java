package com.project.se.subcriber;

import com.project.se.service.EstateSocketService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PushCallback implements MqttCallback {
    @Autowired
    private EstateSocketService estateSocketService;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(cause);
        System.out.println("Lost connection");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {
            String data = new String(message.getPayload());
            JSONParser jsonParser = new JSONParser();
            JSONArray payload = (JSONArray) jsonParser.parse(data);
            JSONObject values = (JSONObject) payload.get(0);
            System.out.println(values);
            Object value = values.get("values");
            String geoLocation = value.toString();
            estateSocketService.sendMessageToTopic(geoLocation);
        }catch (Exception e){
            System.out.println("Error occur");
            System.out.println(e);
        }
    }
}
