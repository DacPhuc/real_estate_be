package com.project.se.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "real_estate")
@Data
public class Estate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int index;
    private String addr_street;
    private String addr_ward;
    private String addr_district;
    private String addr_city;
    private String surrounding;
    private String surrounding_name;
    private String surrounding_characteristics;
    private String transaction_type;
    private String realestate_type;
    private String position;
    private String legal;
    private String potential;
    private String area;
    private String area_unit;
    private String interior_floor;
    private String interior_room;
    private String price;
    private String phone;
    private String email;
    private String others;
    private String url;
    private String title;

}
