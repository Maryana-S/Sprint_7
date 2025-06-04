package ru.practicum.services.qa.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum.services.qa.scooter.models.CreateOrderSuccessResponse;
import ru.practicum.services.qa.scooter.models.PostOrdersRequest;
import ru.practicum.services.qa.scooter.utils.Requests;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class PostOrdersTest extends BaseTest {

    private String[] color;

    public Integer trackId;

    public PostOrdersTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные: color = {0}")
    public static Object[][] getCredentials() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    @Test
    @DisplayName("Проверка, что при отправке POST запроса на эндпоинт /api/v1/orders с различными значениями color заказ успешно создается")
    @Description("Отправка POST запроса на эндпоинт /api/v1/orders с различными значениями color. Проверка успешного создания заказа")
    public void postOrdersDifferentColorsReturnTrackTest() {
        PostOrdersRequest postOrders = new PostOrdersRequest(color);
        CreateOrderSuccessResponse orderSuccessResponse = Requests.postOrders(postOrders)
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .as(CreateOrderSuccessResponse.class);
        trackId = orderSuccessResponse.getTrack();
        assertThat(trackId, instanceOf(Integer.class));
    }

    @After
    @Step("Отмена созданных заказов")
    public void cancelOrder() {
        Requests.cancelOrder(trackId);
    }

}
