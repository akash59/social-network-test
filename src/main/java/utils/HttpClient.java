package utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class HttpClient {
    private final String baseUrl;
    private final String contentType = "application/json";
    private final Gson gson;
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    public HttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.gson = new Gson();
    }

    private RequestSpecification prepareRequest() {
        return given()
                .contentType(contentType)
                .baseUri(baseUrl);
    }

    public Response makePostRequest(String endpoint, Object requestBody) {
        String json = gson.toJson(requestBody);
        logger.debug("Making POST request to {} with body {}", endpoint, json);
        Response response = prepareRequest().body(json).post(endpoint);
        logger.debug("Received response: {}", response.asString());
        return response;
    }

    public Response makePutRequest(String endpoint, Object requestBody) {
        String json = gson.toJson(requestBody);
        logger.debug("Making PUT request to {} with body {}", endpoint, json);
        Response response = prepareRequest().body(json).put(endpoint);
        logger.debug("Received response: {}", response.asString());
        return response;
    }

    public Response makeGetRequest(String endpoint) {
        logger.debug("Making GET request to {}", endpoint);
        Response response =  prepareRequest().get(endpoint);
        logger.debug("Received response: {}", response.asString());
        return response;
    }

    public Response makePatchRequest(String endpoint, Object requestBody) {
        String json = gson.toJson(requestBody);
        return prepareRequest().body(json).patch(endpoint);
    }

    public Response makeDeleteRequest(String endpoint) {
        return prepareRequest().delete(endpoint);
    }

    public HttpStatusCodeType getHttpStatusCodeType(int statusCode) {
        if (statusCode >= 200 && statusCode < 300) {
            return HttpStatusCodeType.SUCCESSFUL;
        } else if (statusCode >= 300 && statusCode < 400) {
            return HttpStatusCodeType.REDIRECTION;
        } else if (statusCode >= 400 && statusCode < 500) {
            return HttpStatusCodeType.CLIENT_ERROR;
        } else if (statusCode >= 500 && statusCode < 600) {
            return HttpStatusCodeType.SERVER_ERROR;
        } else {
            return HttpStatusCodeType.OTHER;
        }
    }

    public <T> T parseResponse(Response response, Class<T> returnType) throws JsonParseException {
        HttpStatusCodeType statusCodeType = getHttpStatusCodeType(response.getStatusCode());
        switch (statusCodeType) {
            case SUCCESSFUL:
            case CREATED:
                return gson.fromJson(response.asString(), returnType);
            case REDIRECTION:
            case CLIENT_ERROR:
            case SERVER_ERROR:
            default:
                throw new RuntimeException("Failed to get a valid response or bad status code: " + response.getStatusCode());
        }
    }
}
