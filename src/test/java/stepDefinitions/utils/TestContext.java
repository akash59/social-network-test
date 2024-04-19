package stepDefinitions.utils;

import io.restassured.response.Response;
import models.Post;
import utils.HttpClient;

public class TestContext {
    private static HttpClient httpClient = null;
    private Response response;
    private Post createdPost;
    private int postId; // Store the post ID

    public TestContext() {
        this.httpClient = new HttpClient("http://jsonplaceholder.typicode.com");
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }

    public Response getResponse() {
        return response;
    }

    public void setCreatedPost(Post createdPost) {
        this.createdPost = createdPost;
    }

    public Post getCreatedPost() {
        return createdPost;
    }
}

