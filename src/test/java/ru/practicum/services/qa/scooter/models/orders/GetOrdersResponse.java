package ru.practicum.services.qa.scooter.models.orders;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetOrdersResponse {

    private Orders[] orders;

    private PageInfo pageInfo;

    private AvailableStations[] availableStations;

}
