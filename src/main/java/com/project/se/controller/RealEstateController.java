package com.project.se.controller;

import com.project.se.domain.Estate;
import com.project.se.repository.EstateRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RealEstateController {
    @Autowired
    EstateRepository estateRepository;

    @GetMapping("/hello")
    public ResponseEntity<?> getMethod(){
        Estate estate = estateRepository.findById(1)
                .orElse(new Estate());
        JSONObject jsonObject = new JSONObject();
        System.out.println(estate.toString());
        jsonObject.put("result", estate);
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
}
