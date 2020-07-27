package com.herokuapp.restfulbooker;

import herokuapp.com.restfulbooker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTests extends BaseTest {

    @Test
    public void partialUpdateBookingTest() {
        //create booking
        Response createResponse = createBooking();
        createResponse.print();

        // get booking id
        int bookingId = createResponse.jsonPath().getInt("bookingid");

        //patch booking
        JSONObject body = new JSONObject();

        body.put("firstname", "Ania");

        JSONObject bookingdates = new JSONObject();

        bookingdates.put("checkin", "2020-03-28");
        bookingdates.put("checkout", "2020-03-29");

        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "piesek");

        Response responsePatch = RestAssured.given().auth().preemptive().basic("admin", "password123").
                contentType(ContentType.JSON).body(body.toString()).patch("https://restful-booker.herokuapp.com/booking/" + bookingId);

        responsePatch.print();

        Assert.assertEquals(responsePatch.getStatusCode(), 200);

        String actualFirstname = responsePatch.jsonPath().getString("firstname");
        String actualLastname = responsePatch.jsonPath().getString("lastname");
        int actualPrice = responsePatch.jsonPath().getInt("totalprice");
        boolean isDepositPaid = responsePatch.jsonPath().getBoolean("depositpaid");
        String checkin = responsePatch.jsonPath().getString("bookingdates.checkin");
        String checkout = responsePatch.jsonPath().getString("bookingdates.checkout");
        String additiopnalNeeds = responsePatch.jsonPath().getString("additionalneeds");

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actualFirstname, "Ania");
        softAssert.assertEquals(actualLastname, "Nowag");
        softAssert.assertEquals(actualPrice, 2137);
        softAssert.assertFalse(isDepositPaid);
        softAssert.assertEquals(checkin, "2020-03-28");
        softAssert.assertEquals(checkout, "2020-03-29");
        softAssert.assertEquals(additiopnalNeeds, "piesek");

        softAssert.assertAll();
    }
}