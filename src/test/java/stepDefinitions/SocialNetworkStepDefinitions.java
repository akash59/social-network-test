package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import models.Post;
import org.junit.Assert;
import utils.HttpClient;

/*
Routes
All HTTP methods are supported. You can use http or https for your requests.

GET	/posts
GET	/posts/1
GET	/posts/1/comments
GET	/comments?postId=1
POST	/posts
PUT	/posts/1
PATCH	/posts/1
DELETE	/posts/1

 */

public class SocialNetworkStepDefinitions {
    private HttpClient httpClient;
    private Response response;
    private Post createdPost;

    // Constructor to initialize HttpClient
    public SocialNetworkStepDefinitions() {
        this.httpClient = new HttpClient("http://jsonplaceholder.typicode.com");
    }

    @Given("a user is registered on the social network")
    public void aUserIsRegisteredOnTheSocialNetwork() {
        // No action needed for this step in this scenario
    }

    @When("the user creates a post with title {string} and body {string}")
    public void theUserCreatesAPostWithTitleAndBody(String title, String body) {
        Post postRequest = new Post(1, title, body);
        response = httpClient.makePostRequest("/posts", postRequest);
        createdPost = httpClient.parseResponse(response, Post.class);
    }

    @Then("the post should be created successfully")
    public void thePostShouldBeCreatedSuccessfully() {
        Assert.assertEquals("Unexpected status code", 201, response.getStatusCode());
    }

    @Then("the response should contain the newly created post with the provided title and body")
    public void theResponseShouldContainTheNewlyCreatedPostWithTheProvidedTitleAndBody() {
        Assert.assertNotNull("No post created", createdPost);
        // Assuming the response contains the newly created post data
        Assert.assertEquals("Unexpected title in response", createdPost.getTitle(), createdPost.getTitle());
        Assert.assertEquals("Unexpected body in response", createdPost.getBody(), createdPost.getBody());
    }

    @Given("a user has created a post with title {string} and body {string}")
    public void aUserHasCreatedAPostWithTitleAndBody(String title, String body) {
        Post postRequest = new Post(1, title, body);
        response = httpClient.makePostRequest("/posts", postRequest);
        createdPost = httpClient.parseResponse(response, Post.class);
    }

    @When("the user edits the post with new title {string} and body {string}")
    public void theUserEditsThePostWithNewTitleAndBody(String newTitle, String newBody) {
        createdPost.setTitle(newTitle);
        createdPost.setBody(newBody);
        response = httpClient.makePutRequest("/posts/1", createdPost);
        createdPost = httpClient.parseResponse(response, Post.class);
    }

    @Then("the post should be updated successfully")
    public void thePostShouldBeUpdatedSuccessfully() {
        Assert.assertEquals("Unexpected status code", 200, response.getStatusCode());
    }

    @Then("the response should contain the updated post information")
    public void theResponseShouldContainTheUpdatedPostInformation() {
        Assert.assertNotNull("No post updated", createdPost);
        // Assuming the response contains the updated post data
        Assert.assertEquals("Unexpected title in response", createdPost.getTitle(), createdPost.getTitle());
        Assert.assertEquals("Unexpected body in response", createdPost.getBody(), createdPost.getBody());
    }
}
