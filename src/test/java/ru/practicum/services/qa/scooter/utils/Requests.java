package ru.practicum.services.qa.scooter.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.services.qa.scooter.models.CourierLoginRequest;
import ru.practicum.services.qa.scooter.models.CourierRequest;
import ru.practicum.services.qa.scooter.models.PostOrdersRequest;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static ru.practicum.services.qa.scooter.utils.Endpoints.*;

public class Requests {

    @Step("Отправка POST запроса на эндпоинт /api/v1/courier. Создание курьера")
    public static Response postCourier(CourierRequest courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER);
    }

    @Step("Отправка POST запроса на эндпоинт /api/v1/courier/login. Логин курьера в системе")
    public static Response postCourierLogin(CourierLoginRequest courierLogin) {
        return given()
                .header("Content-type", "application/json")
                .body(courierLogin)
                .when()
                .post(COURIER_LOGIN);
    }

    @Step("Отправка POST запроса на эндпоинт /api/v1/orders. Создание заказа")
    public static Response postOrders(PostOrdersRequest postOrders) {
        return given()
                .header("Content-type", "application/json")
                .body(postOrders)
                .when()
                .post(ORDERS);
    }

    @Step("Отправка GET запроса на эндпоинт /api/v1/orders. Получение списка заказов")
    public static Response getOrders() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDERS);
    }

    @Step("Отправка GET запроса с необязательными параметрами на эндпоинт /api/v1/orders. Получение списка заказов")
    public static Response getOrders(Map<String, Object> params) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .queryParams(params)
                .get(ORDERS);
    }

    @Step("Отправка DELETE запроса на эндпоинт /api/v1/courier/:id. Удаление курьера")
    public static void deleteCourier(int courierId) {
        given()
                .header("Content-type", "application/json")
                .when()
                .pathParam("courierId", courierId)
                .delete(COURIER_BY_ID);
    }

    @Step("Отправка PUT запроса на эндпоинт /api/v1/orders/accept/:id. Принять заказ")
    public static void acceptOrder(int orderId, int courierId) {
        given()
                .header("Content-type", "application/json")
                .when()
                .pathParam("orderId", orderId)
                .queryParams("courierId", courierId)
                .put(ACCEPT_ORDERS_BY_ID);
    }

    @Step("Отправка GET запроса на эндпоинт /api/v1/orders/track. Получить заказ по его номеру")
    public static Response getOrderByTrackId(int trackId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .queryParams("t", trackId)
                .get(ORDERS_TRACK);
    }

    @Step("Отправка PUT запроса на эндпоинт /api/v1/orders/finish/:id. Завершить заказ")
    public static void finishOrder(int orderId) {
        given()
                .header("Content-type", "application/json")
                .when()
                .pathParam("orderId", orderId)
                .put(FINISH_ORDER_BY_ID);
    }

    @Step("Отправка PUT запроса на эндпоинт /api/v1/orders/cancel. Отменить заказ")
    public static void cancelOrder(int trackId) {
        given()
                .header("Content-type", "application/json")
                .when()
                .queryParam("track", trackId)
                .put(CANCEL_ORDER);
    }
}
