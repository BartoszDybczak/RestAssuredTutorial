package com.herokuapp.restfulbooker;

import herokuapp.com.restfulbooker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest extends BaseTest {

    @Test
    public void getBookingTestHomework() {
        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Set path parameter
        spec.pathParam("bookingId", responseCreate.jsonPath().getInt("bookingid"));

        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but its not");

        SoftAssert softAssert = new SoftAssert();

        String firstName = response.jsonPath().getString("firstname");
        String lastName = response.jsonPath().getString("lastname");

        softAssert.assertEquals(firstName, "Barteg");
        softAssert.assertEquals(lastName, "Dybczag");

        int price = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 2137);

        boolean depsitepaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depsitepaid);

        String checkin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(checkin, "2020-03-25");

        String checkout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(checkout, "2020-03-27");

        softAssert.assertAll();
    }

    @Test
    public void getBookingXmlTest() {
        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Set path parameter
        spec.pathParam("bookingId", responseCreate.jsonPath().getInt("bookingid"));

        Header xml = new Header("Accept", "application/xml");
        spec.header(xml);

        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but its not");

        SoftAssert softAssert = new SoftAssert();

        String firstName = response.xmlPath().getString("booking.firstname");
        String lastName = response.xmlPath().getString("booking.lastname");

        softAssert.assertEquals(firstName, "Barteg");
        softAssert.assertEquals(lastName, "Dybczag");

        int price = response.xmlPath().getInt("booking.totalprice");
        softAssert.assertEquals(price, 2137);

        boolean depsitepaid = response.xmlPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depsitepaid);

        String checkin = response.xmlPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(checkin, "2020-03-25");

        String checkout = response.xmlPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(checkout, "2020-03-27");

        softAssert.assertAll();
    }
}