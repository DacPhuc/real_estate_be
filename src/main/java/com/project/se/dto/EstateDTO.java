package com.project.se.dto;

import lombok.Data;

@Data
public class EstateDTO {
    private String city = "";
    private String district = "";
    private String real_type = "";
    private String minPrice = "0";
    private String maxPrice = "10";
}
