package com.herokuapp.restfulbooker;

import herokuapp.com.restfulbooker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTest extends BaseTest {

    @Test
    public void getBookingIdsWithoutFilterTest() {
        // get response
        Response response = RestAssured.given(spec).get("/booking");
        response.print();

        // verify status code is 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it's not");

        //verify at least 1 booking id is inside the response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of booking ids is empty. It shouldn't");
    }

    @Test
    public void getBookingIdsWithFilterTest() {
        //Add query parameter to spec
        spec.queryParam("firstname", "Susan");
        spec.queryParam("lastname", "Brown");

        // get response
        Response response = RestAssured.given(spec).get("/booking");
        response.print();
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it's not");

        //verify at least 1 booking id is inside the response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of booking ids is empty. It shouldn't");
    }
}