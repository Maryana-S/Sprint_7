package ru.practicum.services.qa.scooter.models;

import lombok.Data;

@Data
public class PostOrdersRequest {

    private String firstName;

    private String lastName;

    private String address;

    private String metroStation;

    private String phone;

    private Integer rentTime;

    private String deliveryDate;

    private String comment;

    private String[] color;

    public PostOrdersRequest(String[] color){
        firstName = "Звёздочка";
        lastName = "Баттерфляй";
        address = "Мьюни, Королевский дворец";
        metroStation = "4";
        phone = "+7 999 999 99 99";
        rentTime = 7;
        deliveryDate = "2025-07-06";
        comment = "На землю! К Марко <3";
        this.color = color;
    }

    public PostOrdersRequest() {
        firstName = "Звёздочка";
        lastName = "Баттерфляй";
        address = "Мьюни, Королевский дворец";
        metroStation = "4";
        phone = "+7 999 999 99 99";
        rentTime = 7;
        deliveryDate = "2025-07-06";
        comment = "На землю! К Марко <3";
        color = new String[]{"BLACK"};
    }

}
