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
}
