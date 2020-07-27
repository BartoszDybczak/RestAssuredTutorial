package com.herokuapp.restfulbooker;

import herokuapp.com.restfulbooker.BaseTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class HealthCheckTest extends BaseTest {

    @Test
    public void healthCheckTest() {

        given().
                spec(spec).
                when().
                get("/ping").
                then().
                assertThat().
                statusCode(201);
    }
}
