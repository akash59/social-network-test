package stepDefinitions.comments;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import stepDefinitions.utils.TestContext;

public class CommentSteps {

    private TestContext testContext;

    public CommentSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("the user retrieves comments for post with ID {int}")
    public void theUserRetrievesCommentsForPostWithID(int postId) {
        Response response = testContext.getHttpClient().makeGetRequest("/posts/" + postId + "/comments");
        testContext.setResponse(response);
    }

    @Then("the response should contain comments for post with ID {int}")
    public void theResponseShouldContainCommentsForPostWithID(int postId) {
        Response response = testContext.getResponse();
        String responseBody = response.getBody().asString();
        Assert.assertTrue("Response does not contain any comments", responseBody.length() > 0);
    }
}
