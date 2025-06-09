package ru.practicum.services.qa.scooter.models.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvailableStations {

    private String name;

    private String number;

    private String color;

}
