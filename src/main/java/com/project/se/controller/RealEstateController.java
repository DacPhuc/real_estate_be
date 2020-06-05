package com.project.se.controller;

import com.project.se.domain.Estate;
import com.project.se.repository.EstateRepository;
import com.project.se.service.EstateService;
import com.project.se.service.EstateSocketService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RealEstateController {
    @Autowired
    EstateRepository estateRepository;

    @Autowired
    EstateService estateService;

    @Autowired
    EstateSocketService estateSocketService;

    @GetMapping("/estates")
    public ResponseEntity<?> getMethod(@RequestParam(name = "page") int pageNumber, @RequestParam(name = "pageSize") int pageSize){
        Pageable pagination = PageRequest.of(pageNumber, pageSize, Sort.by("index").ascending());
        Page<Estate> estate = estateRepository.findAll(pagination);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", estate);
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @GetMapping("/estates/geolocation")
    public ResponseEntity<?> getGeolocation(@RequestParam int id){
        try {
            return estateService.getGeolocation(id);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Can't find estate have id " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estates/light")
    public ResponseEntity<?> turnLight(@RequestParam int status) throws MqttException {
        estateService.pushMessageToMqtt(status);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
