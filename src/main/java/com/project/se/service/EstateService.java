package com.project.se.service;

import com.project.se.config.GoogleMapConfig;
import com.project.se.domain.Estate;
import com.project.se.repository.EstateRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class EstateService {
    @Autowired
    EstateRepository estateRepository;

    @Autowired
    GoogleMapConfig googleMapConfig;

    @Value("${qjzh.mqtt.host}")
    private String serverInfo;

    public ResponseEntity getGeolocation(int estateId) throws Exception {
        Estate estate = estateRepository.findById(estateId).orElseThrow(() -> new Exception("Can find estate have id" + estateId));
        if (estate.getGeo_location() != null){
            return new ResponseEntity(estate.getGeo_location(), HttpStatus.OK);
        }
        String street = estate.getAddr_street();
        String ward = estate.getAddr_ward();
        String city = estate.getAddr_city();
        String district = estate.getAddr_district();
        String googleUrl = googleMapConfig.getApiUrl();
        String apiKey = googleMapConfig.getApiKey();
        String url = googleUrl + street + "," + ward + "," + district + "," + city + "&key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> location = restTemplate.getForEntity(url, String.class);
        JSONParser jsonParser = new JSONParser();
        JSONObject geoLocation = (JSONObject) jsonParser.parse(location.getBody());
        estate.setGeo_location(geoLocation);
        estateRepository.save(estate);
        return location;
    }

    public void pushMessageToMqtt(String status) throws MqttException {
        try {
            MqttClient client = new MqttClient(serverInfo, "Group_8", null);
            client.connect();
            MqttMessage message = new MqttMessage();
            message.setPayload(status.getBytes());
            client.publish("Topic/LightD", message);
        } catch (MqttException e){
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println("An error occur when send message to control light");
        }
    }
}
