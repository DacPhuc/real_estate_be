package com.project.se.controller;

import com.project.se.domain.Estate;
import com.project.se.repository.EstateRepository;
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
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class RealEstateController {
    @Autowired
    EstateRepository estateRepository;

    @GetMapping("/estates")
    public ResponseEntity<?> getMethod(@RequestParam(name = "page") String pageNumber, @RequestParam(name = "pageSize") String pageSize){
        int page = Integer.parseInt(pageNumber);
        int size = Integer.parseInt(pageSize);
        Pageable pagination = PageRequest.of(page, size);
        Page<Estate> estate = estateRepository.findAll(pagination);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", estate);
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @GetMapping("/estates/geolocation")
    public ResponseEntity<?> getGeolocation(@RequestParam String id){
        int estate_id = Integer.parseInt(id);
        Estate estate = estateRepository.findById(estate_id).orElse(null);
        RestTemplate restTemplate = new RestTemplate();
        String street = estate.getAddr_street();
        String ward = estate.getAddr_ward();
        String city = estate.getAddr_city();
        String district = estate.getAddr_district();
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + street + "," + ward + "," + district + "," + city +"&key=AIzaSyCCV-Z0WSK9z5bTLUTZ13s5YVz6I-b2_oE";
        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);
        return response;
    }
}
