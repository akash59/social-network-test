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
  Scenario Outline: User edits a post (happy path)
    When the user creates a post with title "<InitialTitle>" and body "<InitialBody>"
    Then the response should contain 201 status code
    When the user edits the post with new title "<UpdatedTitle>" and body "<UpdatedBody>"
    Then the response should contain 200 status code
    Then the response should contain the post information with title "<UpdatedTitle>" and body "<UpdatedBody>"

    Examples:
      | InitialTitle  | InitialBody  | UpdatedTitle  | UpdatedBody  |
      | Initial Title | Initial Body | Updated Title | Updated Body |
      | Old Title     | Old Body     | New Title     | New Body     |

  @RegressionTest
  Scenario: User retrieves all posts
    Given the user retrieves the count of all posts
    When the user creates a post with title "my_post" and body "posting something"
    Then the response should contain 201 status code
    Then the count of posts should "increase" by 1

  @RegressionTest
  Scenario Outline: User deletes a post
    Given the user retrieves the count of all posts
    When the user creates a post with title "<Title>" and body "<Body>"
    When the user deletes the post
    Then the post should be deleted successfully
    And the response should contain 200 status code
    And the count of posts should "decrease" by 1

    Examples:
      | Title           | Body                |
      | my post         | posting something   |
      | my another post | reposting something |

  @RegressionTest
  Scenario Outline: User retrieves comments for a post
    When the user retrieves comments for post with ID <PostID>
    Then the response should contain comments for post with ID <PostID>

    Examples:
      | PostID |
      | 1      |
      | 2      |
      | 3      |

#  @RegressionTest
#  Scenario: User cannot create a post with invalid data
#    When the user creates a post with title "" and body ""
#    Then the response should contain 400 status code
  @RegressionTest
  Scenario: User cannot view a non-existent post
    When the user retrieves a post by post id -1
    Then the response should contain 404 status code

  @RegressionTest
  Scenario: User cannot delete a non-existent post
    When the user deletes a post by id -100
    Then the response should contain 200 status code