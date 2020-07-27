package com.herokuapp.restfulbooker;

import herokuapp.com.restfulbooker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HeadersAndCookiesTest extends BaseTest {

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

    @Test
    public void headersAndCookiesTest() {

        Header someHader = new Header("Some name", "Some value");
        spec.header(someHader);

        Cookie someCookie = new Cookie.Builder("some cookie name", "some cookie value").build();
        spec.cookie(someCookie);

        Response response = RestAssured.given(spec).
                cookie("testCookieName", "testCookieValue").
                header("testHeaderName", "testHeaderValue").log().all().get("/ping");

        //Get headers
        Headers headers = response.getHeaders();
        System.out.println("Headers " + headers);

        Header serverHeader1 = headers.get("Server");
        System.out.println(serverHeader1.getName() + " : " + serverHeader1.getValue());

        String serverHeader2 =  response.getHeader("Server");
        System.out.println("Server " + serverHeader2);


        //Get cookies
        Cookies cookies = response.getDetailedCookies();
        System.out.println("Cookies " + cookies);

    }
}
