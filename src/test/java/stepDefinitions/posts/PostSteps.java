package stepDefinitions.posts;

import api.ApiEndpoints;
import api.ApiUrlBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import models.Post;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stepDefinitions.utils.TestContext;

import java.util.ArrayList;

public class PostSteps {

    private static final Logger logger = LoggerFactory.getLogger(PostSteps.class);
    private TestContext testContext;

    public PostSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("a user is registered on the social network")
    public void aUserIsRegisteredOnTheSocialNetwork() {
        // No action needed for this step in this scenario
    }

    /**
     * Creates a new post with the provided title and body.
     *
     * @param title The title of the post.
     * @param body  The body of the post.
     */
    @When("the user creates a post with title {string} and body {string}")
    public void theUserCreatesAPostWithTitleAndBody(String title, String body) {
        Post postRequest = new Post(1, title, body, new ArrayList<>());
        Response response = testContext.getHttpClient().makePostRequest(ApiUrlBuilder.buildPostUrl(), postRequest);
        int postId = Integer.parseInt(response.path("id").toString());
        testContext.setPostId(postId);
        testContext.setResponse(response);
        testContext.setCreatedPost(testContext.getHttpClient().parseResponse(response, Post.class));
        Assert.assertEquals("Unexpected status code", 201, response.getStatusCode());
    }

    /**
     * Verifies the response status code.
     *
     * @param statusCode The expected status code.
     */
    @Then("the response should contain {int} status code")
    public void the_post_should_successful(int statusCode) {
        Response response = testContext.getResponse();
        Assert.assertEquals("Unexpected status code", statusCode, response.getStatusCode());
    }

    /**
     * Verifies that the response contains the newly created post information.
     *
     * @param expectedTitle The expected title of the post.
     * @param expectedBody  The expected body of the post.
     */
    @Then("the response should contain the post information with title {string} and body {string}")
    public void theResponseShouldContainThePostInformationWithTitleAndBody(String expectedTitle, String expectedBody) {
        Post post = testContext.getCreatedPost();
        Assert.assertNotNull("No post updated", post);
        Assert.assertEquals("Unexpected title in response", expectedTitle, post.getTitle());
        Assert.assertEquals("Unexpected body in response", expectedBody, post.getBody());
    }

    /**
     * Edits the existing post with the provided new title and body.
     *
     * @param newTitle The new title for the post.
     * @param newBody  The new body for the post.
     */
    @When("the user edits the post with new title {string} and body {string}")
    public void theUserEditsThePostWithNewTitleAndBody(String newTitle, String newBody) {
        Post updatedPost = testContext.getCreatedPost();
        updatedPost.setTitle(newTitle);
        updatedPost.setBody(newBody);
        Response response = testContext.getHttpClient().makePutRequest(
                ApiUrlBuilder.buildPostUrl(updatedPost.getUserId()),
                updatedPost
        );
        testContext.setResponse(response);
        testContext.setCreatedPost(testContext.getHttpClient().parseResponse(response, Post.class));
    }

    /**
     * Deletes the post.
     */
    @When("the user deletes the post")
    public void theUserDeletesThePost() {
        int postId = testContext.getPostId();
        Response response = testContext.getHttpClient().makeDeleteRequest(ApiUrlBuilder.buildPostUrl(postId));
        testContext.setResponse(response);
    }

    /**
     * Verifies that the post is deleted successfully.
     */
    @Then("the post should be deleted successfully")
    public void thePostShouldBeDeletedSuccessfully() {
        int statusCode = testContext.getResponse().getStatusCode();
        Assert.assertTrue(
                "Post deletion failed",
                statusCode == 200 || statusCode == 204
        );
    }

    /**
     * Verifies that user retrieves the count of all posts.
     */
    @Given("the user retrieves the count of all posts")
    public void theUserRetrievesTheCountOfAllPosts() {
        Response response = testContext.getHttpClient().makeGetRequest(ApiEndpoints.POSTS);
        //  initialPostCount = response.jsonPath().getList("$").size();
    }

    @Then("the count of posts should {string} by {int}")
    public void theResponseShouldContainTheNewlyCreatedPost(String condition, int count) {
        Response response = testContext.getHttpClient().makeGetRequest(ApiEndpoints.POSTS);
        int currentPostCount = response.jsonPath().getList("$").size();
        /*
        Important: the resource will not be really updated on the server, but it will be faked as if.
        So, disabling the count check in the below implementation.
         */

        /*
        if (condition.strip().equalsIgnoreCase("increase")) {
            Assert.assertEquals(
                    "The count of posts did not increase by 1",
                    initialPostCount + count,
                    currentPostCount
            );
        } else if (condition.strip().equalsIgnoreCase("decrease")) {
            Assert.assertEquals(
                    "The count of posts did not decrease by 1",
                    initialPostCount - count,
                    currentPostCount
            );
        }*/
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param postId The ID of the post to retrieve.
     */
    @When("the user retrieves a post by post id {int}")
    public void theUserRetrievesPostByID(int postId) {
        Response response = testContext.getHttpClient().makeGetRequest(ApiUrlBuilder.buildPostUrl(postId));
        testContext.setResponse(response);
    }

    /**
     * Deletes a post by its ID.
     *
     * @param postId The ID of the post to delete.
     */
    @When("the user deletes a post by id {int}")
    public void theUserDeletesPostByID(int postId) {
        Response response = testContext.getHttpClient().makeDeleteRequest(ApiUrlBuilder.buildPostUrl(postId));
        testContext.setResponse(response);
    }
}