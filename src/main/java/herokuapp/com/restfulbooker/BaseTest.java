package herokuapp.com.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    protected RequestSpecification spec;

    protected Response createBooking() {
        //Create Response body
        JSONObject body = new JSONObject();

        body.put("firstname", "Barteg");
        body.put("lastname", "Dybczag");
        body.put("totalprice", 2137);
        body.put("depositpaid", false);

        JSONObject bookingDates = new JSONObject();

        bookingDates.put("checkin", "2020-03-25");
        bookingDates.put("checkout", "2020-03-27");

        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "piesek");

        // Get Response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).
                body(body.toString()).post("/booking");

        return  response;
    }

    @BeforeMethod
    public void setUp() {
         spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build();
    }
}
