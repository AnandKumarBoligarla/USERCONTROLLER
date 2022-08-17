package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.JsonArray;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.UserController;
import org.hamcrest.Matcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import utils.Endpoints;
import utils.TestNGListener;

import javax.lang.model.element.Name;
import java.io.ObjectInputStream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.TestNGListener.data;


public class MyStepdefs {
    UserController user, user1, user2;
    JsonPath jsonpath;
    JSONObject jsonObject,jsonObject1,jsonObject2;
    JSONArray jsonArray;
    ObjectMapper objectMapper=new ObjectMapper();
    Response response,putResponse;
    UserController responseUser,responseUser1;


    @Given("user details")
    public void userDetails() {
        jsonObject = (JSONObject) TestNGListener.data.get("createRequest");
    }

    @When("creating a user")
    public void creatingAUser() throws JsonProcessingException {

        user= new UserController((String) jsonObject.get("name"),
                (String)jsonObject.get("address"),
                (Long)jsonObject.get("marks"));
        response=given().body(user)
                .when().post(Endpoints.userEndpoints1)
                .then().statusCode(200).extract().response();
        responseUser=objectMapper.readValue(response.asString(),UserController.class);
    }

    @Then("user must be created")
    public void userMustBeCreated() throws JsonProcessingException  {
         //responseUser=objectMapper.readValue(response.asString(),UserController.class);
        Assert.assertEquals(user.getName(),responseUser.getName());
        Assert.assertEquals(user.getAddress(),responseUser.getAddress());
        Assert.assertEquals(user.getMarks(),responseUser.getMarks());
    }


    @When("creating a user with no marks")
    public void creatingAUserWithNoMarks() throws JsonProcessingException {
        user= new UserController((String) jsonObject.get("name"),
                (String)jsonObject.get("address"),
                0);
        response=given().body(user)
                .when().post(Endpoints.userEndpoints1)
                .then().statusCode(200).extract().response();
        responseUser=objectMapper.readValue(response.asString(),UserController.class);
    }

    @Then("user can be created with zero marks")
    public void userCanBeCreatedWithZeroMarks() {
        Assert.assertEquals(user.getName(),responseUser.getName());
        Assert.assertEquals(user.getAddress(),responseUser.getAddress());
    }

    @When("creating a user with no name")
    public void creatingAUserWithNoName() {
        user= new UserController(null,
                (String)jsonObject.get("address"),
                (Long)jsonObject.get("marks"));
        response=given().body(user)
                .when().post(Endpoints.userEndpoints1)
                .then().statusCode(400).extract().response();
    }

    @Then("Name is required error message thrown")
    public void nameIsRequiredErrorMessageThrown() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Name is required");
    }

    @When("creating a user with no address")
    public void creatingAUserWithNoAddress() {
        user= new UserController((String) jsonObject.get("name"),
                null,
                (Long)jsonObject.get("marks"));
        response=given().body(user)
                .when().post(Endpoints.userEndpoints1)
                .then().statusCode(400).extract().response();
    }


    @Then("Address is required error message thrown")
    public void addressIsRequiredErrorMessageThrown() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Address is required");
    }



    @Then("users list must be displayed")
    public void usersListMustBeDisplayed() {
        given().when().get(Endpoints.userEndpoints3).then().statusCode(200).extract().response();
    }

    @Then("user with particular id must be displayed")
    public void userWithParticularIdMustBeDisplayed() {
        given().when().get(Endpoints.userEndpoints4).then().statusCode(200).extract().response();
    }

    @Then("user got deleted")
    public void userGotDeleted() {
        jsonpath = new JsonPath(response.asString());
        int userID;
        userID = jsonpath.getInt("id");
        response = given()
                .body(user)
                .when().delete(Endpoints.userEndpoints5 + "/" + userID)
                .then()
                .statusCode(200).extract().response();
    }

    @Then("internal server error message displayed")
    public void internalServerErrorMessageDisplayed() {
        given().when().delete(Endpoints.userEndpoints6).then().statusCode(500).extract().response();
    }

    @Then("Blank page must be displayed")
    public void blankPageMustBeDisplayed() {
        given().when().get(Endpoints.userEndpoints6).then().statusCode(200).extract().response();
    }


    @When("Updating a user name")
    public void updatingAUserName() throws JsonProcessingException {
        jsonObject= (JSONObject) TestNGListener.data.get("createRequest");
        user = new UserController((String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

        response = given()
                .body(user)
                .when().post(Endpoints.userEndpoints1)
                .then()
                .statusCode(200).extract().response();
        objectMapper = new ObjectMapper();
        responseUser = objectMapper.readValue(response.asString(), UserController.class);

        jsonpath = new JsonPath(response.asString());

    }

    @Then("User name must be updated")
    public void userNameMustBeUpdated() throws JsonProcessingException {
        jsonObject= (JSONObject) TestNGListener.data.get("updateRequest");
        user =  new UserController(jsonpath.getInt("id"),(String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

        Response putresponse = given()
                .body(user)
                .when().put(Endpoints.userEndpoints2)
                .then()
                .statusCode(200).extract().response();
        jsonpath = new JsonPath(putresponse.asString());
        objectMapper = new ObjectMapper();
        responseUser = objectMapper.readValue(putresponse.asString(), UserController.class);
        Assert.assertEquals(user.getName(),responseUser.getName());
        Assert.assertEquals(user.getId(),responseUser.getId());
    }

    @When("updating a user address")
    public void creatingAUserWithAddress() throws JsonProcessingException {
        jsonObject= (JSONObject) TestNGListener.data.get("createRequest");
        user = new UserController((String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

        response = given()
                .body(user)
                .when().post(Endpoints.userEndpoints1)
                .then()
                .statusCode(200).extract().response();
        objectMapper = new ObjectMapper();
        responseUser = objectMapper.readValue(response.asString(), UserController.class);

        jsonpath = new JsonPath(response.asString());
    }

    @Then("user address must be updated")
    public void userAddressMustBeUpdated() throws JsonProcessingException {
        jsonObject= (JSONObject) TestNGListener.data.get("updateRequest");
        user =  new UserController(jsonpath.getInt("id"),(String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

        Response putresponse = given()
                .body(user)
                .when().put(Endpoints.userEndpoints2)
                .then()
                .statusCode(200).extract().response();
        jsonpath = new JsonPath(putresponse.asString());
        objectMapper = new ObjectMapper();
        responseUser = objectMapper.readValue(putresponse.asString(), UserController.class);
        Assert.assertEquals(user.getAddress(),responseUser.getAddress());
        Assert.assertEquals(user.getId(),responseUser.getId());
    }
    @When("updating users marks")
    public void updatingUsersMarks() throws JsonProcessingException {
        jsonObject= (JSONObject) TestNGListener.data.get("createRequest");
        user = new UserController((String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

        response = given()
                .body(user)
                .when().post(Endpoints.userEndpoints1)
                .then()
                .statusCode(200).extract().response();
        objectMapper = new ObjectMapper();
        responseUser = objectMapper.readValue(response.asString(), UserController.class);

        jsonpath = new JsonPath(response.asString());
    }

    @Then("user marks must be updated")
    public void userMarksMustBeUpdated() throws JsonProcessingException {
        jsonObject= (JSONObject) TestNGListener.data.get("updateRequest");
        user =  new UserController(jsonpath.getInt("id"),(String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

        Response putresponse = given()
                .body(user)
                .when().put(Endpoints.userEndpoints2)
                .then()
                .statusCode(200).extract().response();
        jsonpath = new JsonPath(putresponse.asString());
        objectMapper = new ObjectMapper();
        responseUser = objectMapper.readValue(putresponse.asString(), UserController.class);
        Assert.assertEquals(user.getMarks(),responseUser.getMarks());
        Assert.assertEquals(user.getId(),responseUser.getId());
    }


    @When("creating multiple users")
    public void creatingMultipleUsers() throws JsonProcessingException {
       jsonArray = (JSONArray) TestNGListener.data.get("createRequest2");

       jsonObject=(JSONObject) jsonArray.get(0);
       jsonObject1=(JSONObject) jsonArray.get(1);
       jsonObject2=(JSONObject)jsonArray.get(2);

        user = new UserController((String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));
        user1 = new UserController((String) jsonObject1.get("name"),
                (String) jsonObject1.get("address"),
                (Long) jsonObject1.get("marks"));
        user2 = new UserController((String) jsonObject2.get("name"),
                (String) jsonObject2.get("address"),
                (Long) jsonObject2.get("marks"));
    }

    @Then("multiple users must be created")
    public void multipleUsersMustBeCreated() {
        UserController[] array = new UserController[3];

        array[0]=user;
        array[1]=user1;
        array[2]=user2;

        response = given().body(array).when().post(Endpoints.userEndpoints7)
                .then().statusCode(200).extract().response();
    }


}