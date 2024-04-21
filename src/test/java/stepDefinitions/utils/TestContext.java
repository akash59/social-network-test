package stepDefinitions.utils;

import io.restassured.response.Response;
import models.Post;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpClient;

/**
 * The TestContext class manages the test context for HTTP requests and responses.
 * It provides methods for accessing the HTTP client, storing response data, and managing post-related information.
 */
public class TestContext {
    private HttpClient httpClient = null;
    private Response response;
    private Post createdPost;
    private User user;
    private int postId; // Store the post ID
    private static final Logger logger = LoggerFactory.getLogger(TestContext.class);

    /**
     * Constructs a new TestContext object and initializes the HTTP client.
     */
    public TestContext() {
        this.httpClient = new HttpClient(ConfigReader.getBaseUrl());
        logger.info("TestContext initialized with base URL: {}", ConfigReader.getBaseUrl());
    }

    /**
     * Retrieves the HTTP client instance.
     *
     * @return The HTTP client instance.
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Sets the HTTP response received from the server.
     *
     * @param response The HTTP response object.
     */
    public void setResponse(Response response) {
        this.response = response;
    }

    /**
     * Sets the ID of the created post.
     *
     * @param postId The ID of the created post.
     */
    public void setPostId(int postId) {
        this.postId = postId;
    }

    /**
     * Retrieves the ID of the created post.
     *
     * @return The ID of the created post.
     */
    public int getPostId() {
        return postId;
    }

    /**
     * Retrieves the HTTP response received from the server.
     *
     * @return The HTTP response object.
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Sets the post object representing the created post.
     *
     * @param createdPost The post object representing the created post.
     */
    public void setCreatedPost(Post createdPost) {
        this.createdPost = createdPost;
    }

    /**
     * Retrieves the post object representing the created post.
     *
     * @return The post object representing the created post.
     */
    public Post getCreatedPost() {
        return createdPost;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;

    }
}

