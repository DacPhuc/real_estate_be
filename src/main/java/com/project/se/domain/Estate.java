package com.project.se.domain;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "real_estate")
@Data
@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
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
    private String website_name;
    private String price_unit;
    private String realestate_url;
    private String title;
    private String date;
    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    private JSONObject geo_location = null;
}
