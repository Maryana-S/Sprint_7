package ru.practicum.services.qa.scooter;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

public class GetOrdersTests {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void postCourierAllParamSuccessTest() {

    }
}
