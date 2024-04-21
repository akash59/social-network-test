package stepDefinitions.users;

import api.ApiEndpoints;
import api.ApiUrlBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import models.Address;
import models.Company;
import models.Geo;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stepDefinitions.utils.TestContext;
import models.User;

import java.util.List;

public class UserSteps {
    private static final Logger logger = LoggerFactory.getLogger(UserSteps.class);
    private TestContext testContext;

    public UserSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("the user sends a GET request to retrieve all users")
    public void theUserSendsGETRequestToUsersEndpoint() {
        logger.info("Retrieving users via calling {}", ApiUrlBuilder.buildUserUrl());
        Response response = testContext.getHttpClient().makeGetRequest(ApiUrlBuilder.buildUserUrl());
        testContext.setResponse(response);
    }

    @Then("the response should contain a list of users")
    public void theResponseStatusCodeShouldBe() {
        Response response = testContext.getResponse();
        List<User> userList = response.jsonPath().getList(".", User.class);
        Assert.assertNotNull("User list not found in response", userList);
        Assert.assertFalse("User list is empty", userList.isEmpty());
    }

    @When("the user sends a GET request to retrieve the user by ID {int}")
    public void theUserSendsGETRequestToUserEndpoint(int userID) {
        String endpoint = ApiUrlBuilder.buildUserUrl(userID);
        Response response = testContext.getHttpClient().makeGetRequest(endpoint);
        testContext.setResponse(response);
    }

    @Then("the response should contain the user data for ID {int}")
    public void theResponseShouldContainUserDataForID(int userID) {
        Response response = testContext.getResponse();
        User user = response.as(User.class);
        Assert.assertEquals("Unexpected user ID", userID, user.getId());
        // Add additional assertions based on the structure of the response
    }

    @Given("the user wants to create a new user with name {string}, email {string}, and username {string}")
    public void theUserWantsToCreateNewUser(String name, String email, String username) {
        // Optionally perform any setup needed before sending the request
        Address address = new Address("123 Street", "Suite 101", "City", "12345", new Geo("40.7128", "-74.0060"));
        Company company = new Company("Company Name", "Catch Phrase", "BS");
        User newUser = new User(name, username, email, address, "123-456-7890", "www.example.com", company);
        testContext.setUser(newUser);
    }

    @When("the user sends a POST request to create a new user")
    public void theUserSendsPOSTRequestToUsersEndpoint() {
        User newUser = testContext.getUser();
        Response response = testContext.getHttpClient().makePostRequest(ApiUrlBuilder.buildUserUrl(), newUser);
        testContext.setResponse(response);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Response response = testContext.getResponse();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals("Unexpected status code", expectedStatusCode, actualStatusCode);
    }

    @Then("the created user data should match the provided input")
    public void theCreatedUserDataShouldMatchProvidedInput() {
        User newUser = testContext.getUser();
        User createdUser = testContext.getResponse().as(User.class);
        Assert.assertEquals("Unexpected name", newUser.getName(), createdUser.getName());
        Assert.assertEquals("Unexpected email", newUser.getEmail(), createdUser.getEmail());
        Assert.assertEquals("Unexpected username", newUser.getUsername(), createdUser.getUsername());
    }

    @When("the user deletes a user by id {int}")
    public void theUserDeletesByID(int userId) {
        Response response = testContext.getHttpClient().makeDeleteRequest(ApiUrlBuilder.buildUserUrl(userId));
        testContext.setResponse(response);
    }


}
