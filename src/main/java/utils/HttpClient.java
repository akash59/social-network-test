package utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

/**
 * Utility class for making HTTP requests using RestAssured library.
 */
public class HttpClient {
    private final String baseUrl;
    private final String contentType = "application/json";
    private final Gson gson;
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    /**
     * Constructs an HttpClient object with the specified base URL.
     *
     * @param baseUrl the base URL for the HTTP requests
     */
    public HttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.gson = new Gson();
    }

    /**
     * Prepares a request specification with base URI and content type.
     *
     * @return the prepared RequestSpecification object
     */
    private RequestSpecification prepareRequest() {
        return given()
                .contentType(contentType)
                .baseUri(baseUrl);
    }

    /**
     * Makes a POST request to the specified endpoint with the given request body.
     *
     * @param endpoint    the endpoint to send the POST request to
     * @param requestBody the request body object to be sent
     * @return the response received from the server
     */
    public Response makePostRequest(String endpoint, Object requestBody) {
        String json = gson.toJson(requestBody);
        logger.debug("Making POST request to {} with body {}", endpoint, json);
        Response response = prepareRequest().body(json).post(endpoint);
        logger.debug("Received response: {}", response.asString());
        return response;
    }

    /**
     * Makes a PUT request to the specified endpoint with the given request body.
     *
     * @param endpoint    the endpoint to send the PUT request to
     * @param requestBody the request body object to be sent
     * @return the response received from the server
     */
    public Response makePutRequest(String endpoint, Object requestBody) {
        String json = gson.toJson(requestBody);
        logger.debug("Making PUT request to {} with body {}", endpoint, json);
        Response response = prepareRequest().body(json).put(endpoint);
        logger.debug("Received response: {}", response.asString());
        return response;
    }

    /**
     * Makes a GET request to the specified endpoint.
     *
     * @param endpoint the endpoint to send the GET request to
     * @return the response received from the server
     */
    public Response makeGetRequest(String endpoint) {
        logger.debug("Making GET request to {}", endpoint);
        Response response =  prepareRequest().get(endpoint);
        logger.debug("Received response: {}", response.asString());
        return response;
    }

    /**
     * Makes a PATCH request to the specified endpoint with the given request body.
     *
     * @param endpoint    the endpoint to send the PATCH request to
     * @param requestBody the request body object to be sent
     * @return the response received from the server
     */
    public Response makePatchRequest(String endpoint, Object requestBody) {
        String json = gson.toJson(requestBody);
        return prepareRequest().body(json).patch(endpoint);
    }

    /**
     * Makes a DELETE request to the specified endpoint.
     *
     * @param endpoint the endpoint to send the DELETE request to
     * @return the response received from the server
     */
    public Response makeDeleteRequest(String endpoint) {
        return prepareRequest().delete(endpoint);
    }

    /**
     * Determines the type of HTTP status code.
     *
     * @param statusCode the status code to be categorized
     * @return the category of HTTP status code
     */
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

    /**
     * Parses the response body into the specified type.
     *
     * @param response   the response received from the server
     * @param returnType the class type to parse the response body into
     * @param <T>        the type of the response body
     * @return the parsed response body object
     * @throws JsonParseException if there is an error parsing the JSON response
     */
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
