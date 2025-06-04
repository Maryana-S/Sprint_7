package ru.practicum.services.qa.scooter.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.services.qa.scooter.models.orders.Orders;

@Data
@NoArgsConstructor
public class GetOrderResponse {

    private Orders order;

}
