package ru.practicum.services.qa.scooter.models.orders;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Orders {

    private Integer id;

    private Integer courierId;

    private String firstName;

    private String lastName;

    private String address;

    private String metroStation;

    private String phone;

    private Integer rentTime;

    private String deliveryDate;

    private Integer track;

    private String[] color;

    private String comment;

    private String createdAt;

    private String updatedAt;

    private Integer status;

}