package ru.practicum.services.qa.scooter.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierLoginRequest {

    private String login;

    private String password;

}
