Feature: User Management

  @SmokeTest
  Scenario: Retrieve All Users
    When the user sends a GET request to retrieve all users
    Then the response should contain 200 status code
    And the response should contain a list of users

  @SmokeTest
  Scenario Outline: Retrieve a User by ID
    When the user sends a GET request to retrieve the user by ID <userID>
    Then the response should contain 200 status code
    And the response should contain the user data for ID <userID>

    Examples:
      | userID |
      | 1      |
      | 2      |
      | 3      |

  @SmokeTest
  Scenario: Retrieve a User by non-existent ID
    When the user sends a GET request to retrieve the user by ID -1
    Then the response should contain 404 status code

  @SmokeTest
  Scenario Outline: Create a New User
    Given the user wants to create a new user with name "<name>", email "<email>", and username "<username>"
    When the user sends a POST request to create a new user
    Then the response should contain 201 status code
    And the created user data should match the provided input

    Examples:
      | name       | email                    | username   |
      | John Doe   | john.doe@example.com     | johndoe    |
      | Jane Smith | jane.smith@example.com   | janesmith  |
      | Alice Lee  | alice.lee@example.com    | alicelee   |

  @SmokeTest
  Scenario: Delete a existing User
    When the user deletes a user by id 1
    Then the response status code should be 200