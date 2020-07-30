package com.project.se.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class VisualEstateDTO implements Serializable {

    @JsonProperty("city")
    private String city;

    @JsonProperty("district")
    private String district;

    @JsonProperty("transaction")
    private String transaction;

    @JsonProperty("realestate")
    private String realestate;

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getRealestate() {
        return realestate;
    }
}
