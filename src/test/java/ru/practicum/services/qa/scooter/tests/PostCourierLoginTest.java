package ru.practicum.services.qa.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.services.qa.scooter.models.CourierLoginRequest;
import ru.practicum.services.qa.scooter.models.CourierLoginSuccessResponse;
import ru.practicum.services.qa.scooter.models.CourierRequest;
import ru.practicum.services.qa.scooter.utils.Requests;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;

public class PostCourierLoginTest extends BaseTest {

    private final String NOT_FOUND_MESSAGE = "Учетная запись не найдена";
    private final String BAD_REQUEST_MESSAGE = "Недостаточно данных для входа";

    String login;
    String password;
    CourierRequest courierRequest;
    CourierLoginRequest courierLogin;

    @Before
    @Step("Инициализация параметров login, password, courierLogin")
    public void initCredentials() {
        login = RandomStringUtils.randomAlphabetic(13);
        password = RandomStringUtils.randomAlphanumeric(6);
        courierRequest = new CourierRequest(login, password, RandomStringUtils.randomAlphabetic(10));
        Requests.postCourier(courierRequest);
        courierLogin = new CourierLoginRequest(login, password);
    }

    @Test
    @DisplayName("Проверка успешного логина курьера в системе (статус код 200) при отправке всех обязательных параметров в POST запросе на эндпоинт /api/v1/courier/login")
    @Description("Отправка всех обязательных параметров в POST запросе на эндпоинт /api/v1/courier/login. Проверка успешного логина курьера в системе")
    public void postCourierLoginAllParamSuccessTest() {
        Response loginResponse = Requests.postCourierLogin(courierLogin);
        loginResponse
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat().body("id", instanceOf(Integer.class));
    }

    @Test
    @DisplayName("Проверка получения ответа 404 Not Found при отправке POST запроса на эндпоинт /api/v1/courier/login с некорректным логином")
    @Description("Отправке POST запроса на эндпоинт /api/v1/courier/login с некорректным логином. Проверка получения ответа 404 Not Found")
    public void postCourierLoginIncorrectLoginNotFoundTest() {
        courierLogin.setLogin(login + RandomStringUtils.randomAlphabetic(2));
        Requests.postCourierLogin(courierLogin)
                .then()
                .statusCode(SC_NOT_FOUND)
                .and()
                .assertThat().body("message", equalTo(NOT_FOUND_MESSAGE));
    }

    @Test
    @DisplayName("Проверка получения ответа 404 Not Found при отправке POST запроса на эндпоинт /api/v1/courier/login с некорректным паролем")
    @Description("Отправке POST запроса на эндпоинт /api/v1/courier/login с некорректным паролем. Проверка получения ответа 404 Not Found")
    public void postCourierLoginIncorrectPasswordNotFoundTest() {
        courierLogin.setPassword(password + RandomStringUtils.randomAlphabetic(2));
        Requests.postCourierLogin(courierLogin)
                .then()
                .statusCode(SC_NOT_FOUND)
                .and()
                .assertThat().body("message", equalTo(NOT_FOUND_MESSAGE));
    }

    @Test
    @DisplayName("Проверка получения ответа 400 Bad Request при отправке POST запроса на эндпоинт /api/v1/courier/login с пустым значением login")
    @Description("Отправка POST запроса на эндпоинт /api/v1/courier/login с пустым значением login. Проверка получения ответа 400 Bad Request")
    public void postCourierLoginEmptyLoginBadRequestTest() {
        courierLogin.setLogin("");
        Requests.postCourierLogin(courierLogin)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo(BAD_REQUEST_MESSAGE));
    }

    @Test
    @DisplayName("Проверка получения ответа 400 Bad Request при отправке POST запроса на эндпоинт /api/v1/courier/login с пустым значением password")
    @Description("Отправка POST запроса на эндпоинт /api/v1/courier/login с пустым значением password. Проверка получения ответа 400 Bad Request")
    public void postCourierLoginEmptyPasswordBadRequestTest() {
        courierLogin.setPassword("");
        Requests.postCourierLogin(courierLogin)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo(BAD_REQUEST_MESSAGE));
    }

    @After
    @Step("Удаление, созданного курьера")
    public void deleteCourier() {
        CourierLoginRequest createdCourier = new CourierLoginRequest(courierRequest.getLogin(), courierRequest.getPassword());
        int courierId = Requests.postCourierLogin(createdCourier)
                .then()
                .extract()
                .as(CourierLoginSuccessResponse.class)
                .getId();
        Requests.deleteCourier(courierId);
    }
}
