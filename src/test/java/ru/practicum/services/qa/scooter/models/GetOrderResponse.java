package ru.practicum.services.qa.scooter.models;

import ru.practicum.services.qa.scooter.models.orders.Orders;

public class GetOrderResponse {

    private Orders order;

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
}
