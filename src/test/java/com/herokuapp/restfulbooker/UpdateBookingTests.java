package com.herokuapp.restfulbooker;

import herokuapp.com.restfulbooker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTests extends BaseTest {

    @Test
    public void updateBookingTest() {

        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get booking id
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Update booking

        JSONObject body = new JSONObject();

        body.put("firstname", "Ania");
        body.put("lastname", "N.");
        body.put("totalprice", 1964);
        body.put("depositpaid", true);

        JSONObject bookingDates = new JSONObject();

        bookingDates.put("checkin", "2020-03-25");
        bookingDates.put("checkout", "2020-03-27");

        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "piesek");

        // Get Response
        Response responseUpdate = RestAssured.given().auth().preemptive().basic("admin","password123").contentType(ContentType.JSON).
                body(body.toString()).put(" https://restful-booker.herokuapp.com/booking/" + bookingid);

        responseUpdate.print();

        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200 but its not" );

        SoftAssert softAssert = new SoftAssert();

        String firstName = responseUpdate.jsonPath().getString("firstname");
        String lastName = responseUpdate.jsonPath().getString("lastname");

        softAssert.assertEquals(firstName, "Ania");
        softAssert.assertEquals(lastName, "N.");

        int price = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 1964);

        boolean depsitepaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depsitepaid);

        String checkin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(checkin, "2020-03-25");

        String checkout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(checkout, "2020-03-27");

        String actualNeeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualNeeds, "piesek");

        softAssert.assertAll();
    }
}