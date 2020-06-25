package com.project.se.service;

import com.project.se.config.GoogleMapConfig;
import com.project.se.domain.Estate;
import com.project.se.dto.VisualEstateDTO;
import com.project.se.repository.EstateRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class EstateService {
    @Autowired
    EstateRepository estateRepository;

    @Autowired
    GoogleMapConfig googleMapConfig;

    @Value("${qjzh.mqtt.host}")
    private String serverInfo;

    public ResponseEntity getGeolocation(int estateId) throws Exception {
        Estate estate = estateRepository.findById(estateId).orElseThrow(() -> new Exception("Can find estate have id" + estateId));
        if (estate.getGeo_location() != null){
            return new ResponseEntity(estate.getGeo_location(), HttpStatus.OK);
        }
        String street = estate.getAddr_street();
        String ward = estate.getAddr_ward();
        String city = estate.getAddr_city();
        String district = estate.getAddr_district();
        String googleUrl = googleMapConfig.getApiUrl();
        String apiKey = googleMapConfig.getApiKey();
        String url = googleUrl + street + "," + ward + "," + district + "," + city + "&key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> location = restTemplate.getForEntity(url, String.class);
        JSONParser jsonParser = new JSONParser();
        JSONObject geoLocation = (JSONObject) jsonParser.parse(location.getBody());
        estate.setGeo_location(geoLocation);
        estateRepository.save(estate);
        return location;
    }

    public void pushMessageToMqtt(String status) throws MqttException {
        try {
            MqttClient client = new MqttClient(serverInfo, "Group_8", null);
            client.connect();
            MqttMessage message = new MqttMessage();
            message.setPayload(status.getBytes());
            client.publish("Topic/LightD", message);
        } catch (MqttException e){
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println("An error occur when send message to control light");
        }
    }
    public List<String> realEstateTypeList() {
        List<String> realEstateList = estateRepository.realEstateTypeList();
        System.out.println(realEstateList);
        return realEstateList;
    }

    public List<String> transactionTypeList() {
        List<String> realEstateList = new ArrayList<>();
        realEstateList.add("bán");
        realEstateList.add("thuê");
        System.out.println(realEstateList);
        return realEstateList;
    }

    public List<String> cityList(){
        List<String> cityType = new ArrayList<>();
        cityType.add("Hồ Chí Minh");
        cityType.add("Hà Nội");
        return cityType;
    }

    public List<String> districtHCMList(){
        List<String> districtHCMList = estateRepository.districtHCMList();
        System.out.println(districtHCMList);
        return districtHCMList;
    }

    public List<String> districtHNList(){
        List<String> districtHNList = estateRepository.districtHNList();
        System.out.println(districtHNList);
        return districtHNList;
    }

    public Map<Date, Float> getAveragePrice(VisualEstateDTO visualEstate) throws ParseException {
        String city = visualEstate.getCity();
        String dist = visualEstate.getDistrict();
        String realestate_type = visualEstate.getRealestate();
        String transaction = visualEstate.getTransaction();
        List<Object> priceDictList = estateRepository.priceDict(city, dist, realestate_type, transaction);

        String unit;
        if (transaction.equals("bán")) {
            unit = "tỷ";
        }else {
            unit = "triệu/tháng";
        }

        Map<String, List> priceDateList = new HashMap<>();

        Iterator iteratorPrice = priceDictList.iterator();
        System.out.println("aaa");
        while (iteratorPrice.hasNext()) {
            Object[] estate = (Object[]) iteratorPrice.next();
            String date = (String) estate[2];
            if (unit.equals(estate[1])){
                if (priceDateList.get(date) != null){
                    List<Float> prices = priceDateList.get(date);
                    prices.add(Float.parseFloat((String) estate[0]));
                }else {
                    List<Float> prices = new ArrayList<>();
                    prices.add(Float.parseFloat((String) estate[0]));
                    priceDateList.put(date, prices);
                }

            }
        }

        System.out.println(priceDateList);

        Map<Date, Float> priceList = new HashMap<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        for (String date: priceDateList.keySet()){
            List<Float> prices = priceDateList.get(date);
            float sum = 0;
            for (float price: prices){
                sum += price;
            }
            try {
                Date dateFormat = dateFormatter.parse(date);
                priceList.put(dateFormat, sum / prices.size());
            } catch (Exception e) {
                continue;
            }
        }

        return priceList;
    }

}
