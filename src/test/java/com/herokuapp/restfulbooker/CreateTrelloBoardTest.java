package com.herokuapp.restfulbooker;

import herokuapp.com.restfulbooker.CreateTrelloBoard;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateTrelloBoardTest extends herokuapp.com.restfulbooker.CreateTrelloBoard {

    @Test
    public void createTrelloBoardTest() {

        Response trelloResponse = createTrelloBoard("Papaj");

        trelloResponse.print();

        Assert.assertEquals(trelloResponse.getStatusCode(),200);

        String getBoardName = trelloResponse.jsonPath().getString("name");
        String getPermissionLevel = trelloResponse.jsonPath().getString("prefs.permissionLevel");
        String getVoting = trelloResponse.jsonPath().getString("prefs.voting");

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(getBoardName, "Papaj");
        softAssert.assertEquals(getPermissionLevel,"private");
        softAssert.assertEquals(getVoting,"disabled");

        softAssert.assertAll();
    }

    @Test
    public void updateTrelloBoardWithToDo() {

//        Response trelloBoardResponse = createTrelloBoard("Sportowy Świr");

        String trelloBoardId = "5f09e7bb1c8bea80baeea9f7";

        trelloColumnName = "Darek";

        Response trelloBoardUpdateResponse = updateTrelloBorard(trelloBoardId);

        trelloBoardUpdateResponse.print();

        Assert.assertEquals(trelloBoardUpdateResponse.getStatusCode(), 200);

        SoftAssert softAssert = new SoftAssert();

        String getListName = trelloBoardUpdateResponse.jsonPath().getString("name");
        String getClosedValue = trelloBoardUpdateResponse.jsonPath().getString("closed");
        String getIdValue = trelloBoardUpdateResponse.jsonPath().getString("id");

        softAssert.assertEquals(getListName, "Darek");
        softAssert.assertEquals(getClosedValue, "false");
        softAssert.assertEquals(getIdValue, "5f09e7bb1c8bea80baeea9f7");
    }


    @Test
    public void updateTrelloBoardWithDone() {

//        Response trelloBoardResponse = createTrelloBoard("Sportowy Świr");

        String trelloBoardId = "5f09e7bb1c8bea80baeea9f7";

        trelloColumnName = "done";

        Response trelloBoardUpdateResponse = updateTrelloBorard(trelloBoardId);

        trelloBoardUpdateResponse.print();

        Assert.assertEquals(trelloBoardUpdateResponse.getStatusCode(), 200);

        SoftAssert softAssert = new SoftAssert();

        String getListName = trelloBoardUpdateResponse.jsonPath().getString("name");
        String getClosedValue = trelloBoardUpdateResponse.jsonPath().getString("closed");
        String getIdValue = trelloBoardUpdateResponse.jsonPath().getString("id");

        softAssert.assertEquals(getListName, "done");
        softAssert.assertEquals(getClosedValue, "false");
        softAssert.assertEquals(getIdValue, "5f09e7bb1c8bea80baeea9f7");
    }
}


