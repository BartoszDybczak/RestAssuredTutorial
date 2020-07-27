package herokuapp.com.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

public class CreateTrelloBoard {

    protected RequestSpecification trelloSpec;
    protected String trelloKey = "f8391c800d43adb74a53f52e6c40a9b1";
    protected String trelloToken = "f9c17588da1a7cc88810015b5c169f838d9ec9bdbd5e4d1492cc01a6b2a3335c";

    protected String trelloColumnName;

    protected Response createTrelloBoard(String trelloBoardName) {
        Response response = RestAssured.given(trelloSpec).contentType(ContentType.JSON).
                post("/1/boards/?name=" + trelloBoardName + "&key=" + trelloKey + "&token=" + trelloToken + "&defaultLists=false");

        return response;
    }

    protected Response updateTrelloBorard(String trelloBoardId) {

        Response response = RestAssured.given(trelloSpec).contentType(ContentType.JSON).
                post( "/1/lists?name=" + trelloColumnName + "&idBoard=" + trelloBoardId + "&key=" + trelloKey + "&token=" + trelloToken);


        return response;
    }

    @BeforeMethod
    public void trelloSetUp() {
        trelloSpec = new RequestSpecBuilder().setBaseUri("https://api.trello.com").build();
    }
}