package com.project.se.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class PredictionServiceConfig {
    @Value("${real_estate.url}")
    private String priceModelService;
}
