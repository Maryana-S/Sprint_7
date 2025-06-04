package ru.practicum.services.qa.scooter.utils;

public class Endpoints {

    public final static String COURIER = "/api/v1/courier";

    public final static String COURIER_LOGIN = "/api/v1/courier/login";

    public final static String COURIER_BY_ID = "/api/v1/courier/{courierId}";

    public final static String FINISH_ORDER_BY_ID = "/api/v1/orders/finish/{orderId}";

    public final static String CANCEL_ORDER = "/api/v1/orders/cancel";

    public final static String ORDERS = "/api/v1/orders";

    public final static String ORDERS_TRACK = "/api/v1/orders/track";

    public final static String ACCEPT_ORDERS_BY_ID = "/api/v1/orders/accept/{orderId}";

}
