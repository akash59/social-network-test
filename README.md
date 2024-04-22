
# API Test Framework

This API test framework is developed as part of JPMC test assignment.


## Features

- Supports API Test Automation in a BDD fashion.
- Uses Cucumber PicoContainer library for Dependency Injection.
- Support logs generation to debug or analyse any failures during the test run.
- Provides HTML test reports for test runs for easy reports analysis.
- Can support parallel execution if needed without any code change.
- Promotes test steps reusability and test case extendability.
- Post Merge CI support: Using Docker and GitHub Actions, a build is generated on each MR Merge
  and all available tests are executed against that build to ensure none of the existing functionality breaks.



## Tech Stack

**Framework Technologies:** Java, RestAssured, Cucumber

**Json Library:** Gson

**Build Tool:** Maven

**Containerization:** Docker

**CI/CD:** Github Actions




## Framework Structure
```
|-- Dockerfile
|-- pom.xml
`-- src
    |-- main
    |   |-- java
    |   |   |-- api
    |   |   |   |-- ApiEndpoints.java
    |   |   |   `-- ApiUrlBuilder.java
    |   |   |-- models
    |   |   |   |-- Address.java
    |   |   |   |-- Comment.java
    |   |   |   |-- Company.java
    |   |   |   |-- Geo.java
    |   |   |   |-- Post.java
    |   |   |   `-- User.java
    |   |   `-- utils
    |   |       |-- HttpClient.java
    |   |       `-- HttpStatusCodeType.java
    |   `-- resources
    |       `-- logback.xml
    `-- test
        |-- java
        |   |-- runners
        |   |   `-- TestRunner.java
        |   `-- stepDefinitions
        |       |-- comments
        |       |   `-- CommentSteps.java
        |       |-- posts
        |       |   `-- PostSteps.java
        |       |-- users
        |       |   `-- UserSteps.java
        |       `-- utils
        |           |-- ConfigReader.java
        |           `-- TestContext.java
        `-- resources
            |-- config.properties
            |-- cucumber.properties
            `-- features
                |-- social-network-test.feature
                `-- user-management.feature
```
## Example BDD feature file
```gherkin
Feature: Social Network - Posts and Comments

  Background:
    Given a user is registered on the social network

  @RegressionTest
  Scenario Outline: User creates a post (happy path)
    When the user creates a post with title "<Title>" and body "<Body>"
    Then the response should contain 201 status code
    Then the response should contain the post information with title "<Title>" and body "<Body>"

    Examples:
      | Title         | Body         |
      | Test Title    | Test Body    |
      | Another Title | Another Body |

  @RegressionTest
  Scenario Outline: User retrieves comments for a post
    When the user retrieves comments for post with ID <PostID>
    Then the response should contain comments for post with ID <PostID>

    Examples:
      | PostID |
      | 1      |
      | 2      |
      | 3      |
```

## Dependency Injection Example using PicoContainer

Used in sharing state between steps in Cucumber

```java
public class UserSteps {
    private static final Logger logger = LoggerFactory.getLogger(UserSteps.class);
    private TestContext testContext;

    public UserSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    ...
}
```

## TestContext class used to store the shared test state.
```java
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
}
```

## HttpClient class is provided that acts as a common interface for the test layer.
```java
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
}
```


## Running Tests

To run tests, run the following commands. Please make sure that you have Java11 and Maven installed locally.

```bash
  git clone git@github.com:akash59/social-network-test.git
  mvn clean verify
```

Another way of running tests is via `docker` provided the host machine has docker installed. Run the following commands:
```bash
  docker pull akash59/social-nw-tests
  docker run -v ${HOME}/reports:/home/app/TestReports social-nw-tests
```

## 🔗 Links
- [CI: Build/Publish job run example](https://github.com/akash59/social-network-test/actions/runs/8772299689/job/24071068188)
- [CI: Test job run example](https://github.com/akash59/social-network-test/actions/runs/8772299689/job/24071075229)
- [CI: HTML Test reports](https://github.com/akash59/social-network-test/actions/runs/8772920492/artifacts/1433240665)
