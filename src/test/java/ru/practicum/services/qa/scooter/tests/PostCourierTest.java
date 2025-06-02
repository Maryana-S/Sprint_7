package ru.practicum.services.qa.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.services.qa.scooter.models.CourierRequest;
import ru.practicum.services.qa.scooter.utils.Requests;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

public class PostCourierTest extends BaseTest {

    private final String BAD_REQUEST_MESSAGE = "Недостаточно данных для создания учетной записи";
    private final String CONFLICT_MESSAGE = "Этот логин уже используется";

    CourierRequest courier;

    @Before
    @Step("Инициализация обекта класса CourierRequest")
    public void initCourier() {
        courier = new CourierRequest(RandomStringUtils.randomAlphabetic(13), "test" + RandomStringUtils.randomNumeric(3), RandomStringUtils.randomAlphabetic(10));
    }

    @Test
    @DisplayName("Проверка успешного создания курьера при отправке POST запроса на эндпоинт /api/v1/courier со всеми полями")
    @Description("Успешное создание курьера методом POST /api/v1/courier. Заполнены все поля тела запроса")
    public void postCourierAllParamSuccessTest() {
        Requests.postCourier(courier)
                .then()
                .statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка успешного создания курьера при отправке POST запроса на эндпоинт /api/v1/courier с пустым значением в поле firstName")
    @Description("Успешное создание курьера методом POST /api/v1/courier. Заполнены только обязательные поля тела запроса")
    public void postCourierWithoutFirstNameSuccessTest() {
        courier.setFirstName("");
        Requests.postCourier(courier)
                .then()
                .statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка получения ответа 400 Bad Request при отправке POST запроса на эндпоинт /api/v1/courier с пустым значением login")
    @Description("Получение ошибки 400 Bad Request при создании курьера методом POST /api/v1/courier. Пустое значение в поле login")
    public void postCourierWithoutLoginBadRequestTest() {
        courier.setLogin("");
        Requests.postCourier(courier)
                .then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo(BAD_REQUEST_MESSAGE));
    }

    @Test
    @DisplayName("Проверка получения ответа 400 Bad Request при отправке POST запроса на эндпоинт /api/v1/courier с пустым значением password")
    @Description("Получение ошибки 400 Bad Request при создании курьера методом POST /api/v1/courier. Пустое значение в поле password")
    public void postCourierWithoutPasswordBadRequestTest() {
        courier.setPassword("");
        Requests.postCourier(courier)
                .then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo(BAD_REQUEST_MESSAGE));
    }

    @Test
    @DisplayName("Проверка получения ответа 409 Сonflict при отправке POST запроса на эндпоинт /api/v1/courier с повторяющимся логином")
    @Description("Получение ошибки 409 Сonflict при создании курьера c повторяющимся логином методом POST /api/v1/courier")
    public void postCourierSameLoginConflictTest() {
        Requests.postCourier(courier);
        courier.setPassword(RandomStringUtils.randomAlphanumeric(6));
        courier.setFirstName(RandomStringUtils.randomAlphabetic(10));
        Requests.postCourier(courier)
                .then()
                .statusCode(409)
                .and()
                .assertThat().body("message", containsString(CONFLICT_MESSAGE));
    }

}
