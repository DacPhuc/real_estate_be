package com.project.se.subcriber;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class PushCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Lost connection");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Topic is : " + topic);
        System.out.println("Message : " + message.getQos());
        System.out.println("Message : " + new String(message.getPayload()));
    }
}
