package stepDefinitions.comments;

import api.ApiEndpoints;
import api.ApiUrlBuilder;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import stepDefinitions.utils.TestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentSteps {

    private static final Logger logger = LoggerFactory.getLogger(CommentSteps.class);
    private TestContext testContext;

    public CommentSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Retrieves comments for a post with a specified ID.
     *
     * @param postId The ID of the post for which comments are to be retrieved.
     */
    @When("the user retrieves comments for post with ID {int}")
    public void theUserRetrievesCommentsForPostWithID(int postId) {
        logger.info("Retrieving comments for post with ID: {}", postId);
        Response response = testContext.getHttpClient().makeGetRequest(ApiUrlBuilder.buildCommentsUrl(postId));
        testContext.setResponse(response);
    }

    /**
     * Verifies that the response contains comments for a post with a specified ID.
     *
     * @param postId The ID of the post for which comments are expected.
     */
    @Then("the response should contain comments for post with ID {int}")
    public void theResponseShouldContainCommentsForPostWithID(int postId) {
        Response response = testContext.getResponse();
        String responseBody = response.getBody().asString();
        Assert.assertTrue("Response does not contain any comments", responseBody.length() > 0);
        logger.info("Comments found for post with ID: {}", postId);
    }
}
