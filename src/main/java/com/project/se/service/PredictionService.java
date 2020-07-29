package com.project.se.service;

import com.project.se.config.PredictionServiceConfig;
import com.project.se.dto.PricingPredictDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PredictionService {

    @Autowired
    PredictionServiceConfig predictionServiceConfig;

    public float getPredictionPrice(PricingPredictDTO pricingPredictDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String price = restTemplate.postForObject(predictionServiceConfig.getPriceModelService(), pricingPredictDTO, String.class);
        return Float.parseFloat(price);
    }
}
