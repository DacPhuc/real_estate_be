package com.project.se.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PricingPredictDTO {
    @NotBlank
    private String transaction_type;
    @NotBlank
    private String city;
    @NotBlank
    private String district;
    @NotBlank
    private String area;
    @NotBlank
    private String real_type;
    @NotBlank
    private String yard;
    @NotBlank
    private String balcony;
    @NotBlank
    private String floor;
}
