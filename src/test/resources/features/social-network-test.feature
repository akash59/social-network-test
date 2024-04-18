Feature: Social Network - Posts and Comments
  @RegressionTest
  Scenario: User creates a post (happy path)
    Given a user is registered on the social network
    Given a user is registered on the social network
    When the user creates a post with title "Test Title" and body "Test Body"
    Then the post should be created successfully
    And the response should contain the newly created post with the provided title and body

  @RegressionTest
  Scenario: User edits a post (happy path)
    Given a user has created a post with title "Initial Title" and body "Initial Body"
    When the user edits the post with new title "Updated Title" and body "Updated Body"
    Then the post should be updated successfully
    And the response should contain the updated post information

  Scenario: User cannot create a post with invalid data
    Given a user is registered on the social network
    When the user attempts to create a post with:
    * Empty title
    * Empty body
    * Title exceeding character limit (if applicable)
    * Body exceeding character limit (if applicable)
    Then the post creation should fail
    And the response should contain an error message indicating the specific validation error

  Scenario: User cannot edit a post with invalid data
    Given a user has created a post
    When the user attempts to edit the post with:
    * Empty title
    * Empty body
    * Title exceeding character limit (if applicable)
    * Body exceeding character limit (if applicable)
    Then the post update should fail
    And the response should contain an error message indicating the specific validation error

  Scenario: User views a list of posts
    Given there are existing posts on the social network
    When the user retrieves the list of posts
    Then the response should contain a list of post objects
    And each post object should contain essential details like title, body, author (if applicable)

  Scenario: User views a specific post
    Given a post exists on the social network
    When the user retrieves the post by its ID
    Then the response status code should be 200 (OK)
    And the response should contain the post object with all details

  Scenario: User cannot view a non-existent post
    Given a non-existent post ID
    When the user retrieves a post by the ID
    Then the response status code should be 404 (NOT FOUND)
    And the response should contain an error message indicating the post not found

  Scenario: User comments on a post (happy path)
    Given a user is registered and a post exists
    When the user creates a comment on the post with name and body
    Then the comment should be created successfully
    And the response should contain the newly created comment object

  Scenario: User cannot comment on a non-existent post
    Given a user is registered and a non-existent post ID
    When the user attempts to create a comment on the post ID
    Then the comment creation should fail
    And the response should contain an error message indicating the post not found

  Scenario: User deletes a post (happy path)
    Given a user has created a post
    When the user deletes the post
    Then the post should be deleted successfully
    And the response status code should be 200 (OK)

  Scenario: User cannot delete a non-existent post
    Given a user attempts to delete a post with a non-existent ID
    Then the deletion should fail
    And the response status code should be 404 (NOT FOUND)
    And the response should contain an error message indicating the post not found