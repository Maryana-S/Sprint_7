package ru.practicum.services.qa.scooter.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.services.qa.scooter.models.CourierLoginRequest;
import ru.practicum.services.qa.scooter.models.CourierRequest;
import ru.practicum.services.qa.scooter.models.PostOrdersRequest;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Requests {

    @Step("Отправка POST запроса на эндпоинт /api/v1/courier. Создание курьера")
    public static Response postCourier(CourierRequest courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Отправка POST запроса на эндпоинт /api/v1/courier/login. Логин курьера в системе")
    public static Response postCourierLogin(CourierLoginRequest courierLogin) {
        return given()
                .header("Content-type", "application/json")
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Отправка POST запроса на эндпоинт /api/v1/orders. Создание заказа")
    public static Response postOrders(PostOrdersRequest postOrders) {
        return given()
                .header("Content-type", "application/json")
                .body(postOrders)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Отправка GET запроса на эндпоинт /api/v1/orders. Получение списка заказов")
    public static Response getOrders() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");
    }

    @Step("Отправка GET запроса с необязательными параметрами на эндпоинт /api/v1/orders. Получение списка заказов")
    public static Response getOrders(Map<String, Object> params) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .queryParams(params)
                .get("/api/v1/orders");
    }

    @Step("Отправка DELETE запроса на эндпоинт /api/v1/courier/:id. Удаление курьера")
    public static void deleteCourier(int courierId) {
        given()
                .header("Content-type", "application/json")
                .when()
                .pathParam("courierId", courierId)
                .delete("/api/v1/courier/{courierId}");
    }

    @Step("Отправка PUT запроса на эндпоинт /api/v1/orders/accept/:id. Принять заказ")
    public static void acceptOrder(int orderId, int courierId) {
        given()
                .header("Content-type", "application/json")
                .when()
                .pathParam("orderId", orderId)
                .queryParams("courierId", courierId)
                .put("/api/v1/orders/accept/{orderId}");
    }

    @Step("Отправка GET запроса на эндпоинт /api/v1/orders/track. Получить заказ по его номеру")
    public static Response getOrderByTrackId(int trackId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .queryParams("t", trackId)
                .get("/api/v1/orders/track");
    }

    @Step("Отправка PUT запроса на эндпоинт /api/v1/orders/finish/:id. Завершить заказ")
    public static void finishOrder(int orderId) {
        given()
                .header("Content-type", "application/json")
                .when()
                .pathParam("orderId", orderId)
                .put("/api/v1/orders/finish/{orderId}");
    }

    @Step("Отправка PUT запроса на эндпоинт /api/v1/orders/cancel. Отменить заказ")
    public static void cancelOrder(int trackId) {
        given()
                .header("Content-type", "application/json")
                .when()
                .body("{\"track\":" + trackId + "}")
                .put("/api/v1/orders/cancel");
    }
}
