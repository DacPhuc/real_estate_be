package com.project.se.controller;

import com.project.se.domain.Estate;
import com.project.se.dto.EstateDTO;
import com.project.se.dto.PricingPredictDTO;
import com.project.se.dto.VisualEstateDTO;
import com.project.se.repository.EstateRepository;
import com.project.se.service.EstateService;
import com.project.se.service.EstateSocketService;
import com.project.se.service.PredictionService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RealEstateController {
    @Autowired
    EstateRepository estateRepository;

    @Autowired
    EstateService estateService;

    @Autowired
    EstateSocketService estateSocketService;

    @Autowired
    PredictionService predictionService;

    @GetMapping("/estates")
    public ResponseEntity<?> getMethod(@RequestParam(name = "page") int pageNumber, @RequestParam(name = "pageSize") int pageSize){
        Pageable pagination = PageRequest.of(pageNumber, pageSize, Sort.by("index").ascending());
        Page<Estate> estate = estateRepository.findAll(pagination);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", estate);
        System.out.println(jsonObject);
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

    @PostMapping(value = "/estates/light", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> turnLight(@RequestBody String status) throws MqttException {
        System.out.println(status);
        estateService.pushMessageToMqtt(status);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/estates/visualize")
    public ResponseEntity<?> visualize(){
        List<String> realEstateType = estateService.realEstateTypeList();
        List<String> transactionType = estateService.transactionTypeList();
        List<String> cityList = estateService.cityList();
        List<String> districtHCM = estateService.districtHCMList();
        List<String> districtHN = estateService.districtHNList();
        HashMap<String, List> result = new HashMap<>();
        result.put("estate_type", realEstateType);
        result.put("transaction_type", transactionType);
        result.put("city", cityList);
        result.put("HCM", districtHCM);
        result.put("HN", districtHN);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("estates/priceVisual")
    public ResponseEntity<?> priceVisual(@RequestBody VisualEstateDTO visualEstateDTO) throws Exception{
        Map<Date, Float> priceList = estateService.getAveragePrice(visualEstateDTO);
        return new ResponseEntity<>(priceList, HttpStatus.OK);
    }

    @PostMapping("estate/predict")
    public ResponseEntity<?> getPredictionPrice(@RequestBody PricingPredictDTO pricingPredictDTO) {
        float price = predictionService.getPredictionPrice(pricingPredictDTO);
        Map<String, Float> result = new HashMap<>();
        result.put("price", price);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("estates/search")
    public ResponseEntity<?> searchEstate(@RequestBody EstateDTO estateDTO){
        System.out.println(estateDTO);
        return new ResponseEntity<>("Hello nhe", HttpStatus.OK);
    }
}
