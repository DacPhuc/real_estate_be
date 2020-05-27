package com.project.se.service;

import com.project.se.config.GoogleMapConfig;
import com.project.se.domain.Estate;
import com.project.se.repository.EstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class EstateService {
    @Autowired
    EstateRepository estateRepository;

    @Autowired
    GoogleMapConfig googleMapConfig;

    public ResponseEntity getGeolocation(int estateId) throws Exception {
        Estate estate = estateRepository.findById(estateId).orElseThrow(() -> new Exception("Can find estate have id" + estateId));
        String street = estate.getAddr_street();
        String ward = estate.getAddr_ward();
        String city = estate.getAddr_city();
        String district = estate.getAddr_district();
        String googleUrl = googleMapConfig.getApiUrl();
        String apiKey = googleMapConfig.getApiKey();
        String url = googleUrl + street + "," + ward + "," + district + "," + city + "&key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> geoLocation
                = restTemplate.getForEntity(url, String.class);
        return geoLocation;
    }
}
