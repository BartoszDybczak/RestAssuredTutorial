package com.herokuapp.restfulbooker;

import herokuapp.com.restfulbooker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTests extends BaseTest {

    @Test
    public void deleteBookingTest() {

        Response response = createBooking();
        response.print();

        int bookingId = response.jsonPath().getInt("bookingid");

        Response deleteResponse = RestAssured.given(spec).auth().
                preemptive().basic("admin", "password123").delete("/booking/" + bookingId);

        Response responseGet = RestAssured.given(spec).get("/booking/" + bookingId);

        int deleteResponseCode = deleteResponse.getStatusCode();

        Assert.assertEquals(deleteResponseCode, 201);
        Assert.assertEquals(responseGet.getBody().asString(), "Not Found");

        responseGet.print();
        deleteResponse.print();
    }
}