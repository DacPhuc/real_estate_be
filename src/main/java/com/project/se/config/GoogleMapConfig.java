package com.project.se.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GoogleMapConfig {

    @Value("${google.apikey}")
    private String apiKey;

    @Value("${google.geolocation.url}")
    private String apiUrl;
}
