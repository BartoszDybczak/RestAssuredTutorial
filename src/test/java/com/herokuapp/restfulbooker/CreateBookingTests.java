package com.herokuapp.restfulbooker;

import herokuapp.com.restfulbooker.BaseTest;
import herokuapp.com.restfulbooker.Booking;
import herokuapp.com.restfulbooker.Bookingdates;
import herokuapp.com.restfulbooker.Bookingid;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTests extends BaseTest {

    @Test
    public void createBookingTest() {

        Response response = createBooking();
        response.print();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but its not");

        SoftAssert softAssert = new SoftAssert();

        String firstName = response.jsonPath().getString("booking.firstname");
        String lastName = response.jsonPath().getString("booking.lastname");

        softAssert.assertEquals(firstName, "Barteg");
        softAssert.assertEquals(lastName, "Dybczag");

        int price = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(price, 2137);

        boolean depsitepaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depsitepaid);

        String checkin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(checkin, "2020-03-25");

        String checkout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(checkout, "2020-03-27");

        String actualNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualNeeds, "baby crib");

        softAssert.assertAll();
    }

    @Test
    public void createBookingWithPOJOTest() {
        //Create body using POJOs
        Bookingdates bookingdates = new Bookingdates("2020-03-25", "2020-03-27");
        Booking booking = new Booking("Bart", "Dyb", 2137, false, bookingdates, "none");

        // Get Response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(booking).post("/booking");
//        response.print();

        Bookingid bookingid = response.as(Bookingid.class);


        Assert.assertEquals(response.getStatusCode(),200);

        Assert.assertEquals(bookingid.getBooking().toString(),booking.toString());

        System.out.println("Request booking: " + booking.toString());
        System.out.println("Response booking: " + bookingid.getBooking().toString());

//        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but its not");
//
//        SoftAssert softAssert = new SoftAssert();
//
//        String firstName = response.jsonPath().getString("booking.firstname");
//        String lastName = response.jsonPath().getString("booking.lastname");
//
//        softAssert.assertEquals(firstName, "Ola");
//        softAssert.assertEquals(lastName, "Dybczag");
//
//        int price = response.jsonPath().getInt("booking.totalprice");
//        softAssert.assertEquals(price, 2137);
//
//        boolean depsitepaid = response.jsonPath().getBoolean("booking.depositpaid");
//        softAssert.assertFalse(depsitepaid);
//
//        String checkin = response.jsonPath().getString("booking.bookingdates.checkin");
//        softAssert.assertEquals(checkin, "2020-03-25");
//
//        String checkout = response.jsonPath().getString("booking.bookingdates.checkout");
//        softAssert.assertEquals(checkout, "2020-03-27");
//
//        String actualNeeds = response.jsonPath().getString("booking.additionalneeds");
//        softAssert.assertEquals(actualNeeds, "none");
//
//        softAssert.assertAll();
    }
}