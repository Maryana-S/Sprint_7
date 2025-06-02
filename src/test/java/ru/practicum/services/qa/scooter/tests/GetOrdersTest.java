package ru.practicum.services.qa.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.services.qa.scooter.models.*;
import ru.practicum.services.qa.scooter.models.orders.GetOrdersResponse;
import ru.practicum.services.qa.scooter.utils.Requests;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetOrdersTest extends BaseTest {

    String login;
    String password;
    Integer courierId;
    Integer orderId;
    Integer trackId;

    @Before
    @Step("Инициализация параметров: login, password, courierId, trackId, orderId")
    public void initParams() {

        login = RandomStringUtils.randomAlphabetic(13);

        password = RandomStringUtils.randomAlphanumeric(7);

        Requests.postCourier(new CourierRequest(login, password, "Test"));
        courierId = Requests.postCourierLogin(new CourierLoginRequest(login, password))
                .as(CourierLoginSuccessResponse.class)
                .getId();

        trackId = Requests.postOrders(new PostOrdersRequest())
                .as(CreateOrderSuccessResponse.class)
                .getTrack();

        orderId = Requests.getOrderByTrackId(trackId)
                .as(GetOrderResponse.class)
                .getOrder()
                .getId();
    }

    @Test
    @DisplayName("Проверка, что возвращается список заказов, при отправке GET запроса без query параметров на эндпоинт /api/v1/orders")
    @Description("Получение списка заказов. Отправка GET запроса без query параметров на эндпоинт /api/v1/orders")
    public void getOrdersNoQueryParamReturnOrdersTest() {
        GetOrdersResponse ordersResponse = Requests.getOrders()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(GetOrdersResponse.class);
        assertThat("Массив orders пустой", ordersResponse.getOrders().length > 0);
    }

    @Test
    @DisplayName("Проверка, что возвращается список заказов, при отправке GET запроса c courierId на эндпоинт /api/v1/orders")
    @Description("Получение списка заказов. Отправка GET запроса c courierId на эндпоинт /api/v1/orders.")
    public void getOrdersWithCourierIdReturnOrdersTest() {
        Requests.acceptOrder(orderId, courierId);
        Response orders = Requests.getOrders(Map.of("courierId", courierId));
        orders
                .then()
                .statusCode(200);
        assertThat("Массив orders пустой", orders.as(GetOrdersResponse.class).getOrders().length > 0);
        assertThat(courierId, equalTo(orders.as(GetOrdersResponse.class).getOrders()[0].getCourierId()));
    }

    @Test
    @DisplayName("Проверка, что возвращается список заказов, при отправке GET запроса c courierId и nearestStation на эндпоинт /api/v1/orders")
    @Description("Получение списка заказов. Отправка GET запроса c courierId и nearestStation на эндпоинт /api/v1/orders.")
    public void getOrdersWithNearestStationReturnOrdersTest() {
        Requests.acceptOrder(orderId, courierId);
        String metroStationNumber = new PostOrdersRequest().getMetroStation();
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("courierId", courierId);
        queryParams.put("nearestStation", "[\""+ metroStationNumber + "\"]");
        GetOrdersResponse getOrdersResponse = Requests.getOrders(queryParams)
                .then()
                .statusCode(200)
                .extract()
                .as(GetOrdersResponse.class);
        assertThat("Массив orders пустой", getOrdersResponse.getOrders().length > 0);
        assertThat(metroStationNumber, equalTo(getOrdersResponse.getAvailableStations()[0].getNumber()));
        assertThat(courierId, equalTo(getOrdersResponse.getOrders()[0].getCourierId()));
    }

    @Test
    @DisplayName("Проверка, что возвращается список заказов, при отправке GET запроса c limit и page на эндпоинт /api/v1/orders")
    @Description("Получение ограниченного списка заказов на указанной странице. Отправка GET запроса c limit и page на эндпоинт /api/v1/orders.")
    public void getOrdersWithLimitAndPageReturnOrdersTest() {
        int limit = 15;
        int page = 2;
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        queryParams.put("page", page);
        GetOrdersResponse orders = Requests.getOrders(queryParams)
                .then()
                .statusCode(200)
                .extract()
                .as(GetOrdersResponse.class);
        assertThat("Количество заказов на странице больше значения limit",limit >= orders.getOrders().length);
        assertThat(limit, equalTo(orders.getPageInfo().getLimit()));
        assertThat(page, equalTo(orders.getPageInfo().getPage()));
    }

    @Test
    @DisplayName("Проверка, что возвращается список заказов, при отправке GET запроса c limit, page и nearestStation на эндпоинт /api/v1/orders")
    @Description("Получение ограниченного списка заказов на указанной странице и станции метро. Отправка GET запроса c limit, page и nearestStation на эндпоинт /api/v1/orders.")
    public void getOrdersWithLimitPageAndNearestStationReturnOrdersTest() {
        Requests.acceptOrder(orderId, courierId);
        String metroStationNumber = new PostOrdersRequest().getMetroStation();
        int limit = 5;
        int page = 3;
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        queryParams.put("page", page);
        queryParams.put("nearestStation", "[\""+ metroStationNumber + "\"]");
        GetOrdersResponse orders = Requests.getOrders(queryParams)
                .then()
                .statusCode(200)
                .extract()
                .as(GetOrdersResponse.class);
        assertThat("Количество заказов на странице больше значения limit",limit >= orders.getOrders().length);
        assertThat(limit, equalTo(orders.getPageInfo().getLimit()));
        assertThat(page, equalTo(orders.getPageInfo().getPage()));
        assertThat(metroStationNumber, equalTo(orders.getOrders()[0].getMetroStation()));
    }

    @After
    @Step("Удаление тестовых данных")
    public void clearTestData() {
        if(courierId != null) {
            Requests.deleteCourier(courierId);
        }
        if(orderId != null) {
            Requests.finishOrder(orderId);
        }
        if(trackId != null) {
            Requests.cancelOrder(trackId);
        }
    }

}
