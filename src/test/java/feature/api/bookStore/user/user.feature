Feature: User Management

  Scenario: Create a new user
    Given User object is prepared with random username and password
    When I send a POST request to "/Account/v1/User"
    Then The response status code should be 200
    And The response body should contain key "userID"
    And set the response "userID" as an environment variable "id"

  Scenario: User Login
    Given User object is prepared with already created username and password
    When I send a POST request to "/Account/v1/GenerateToken"
    Then set the response "token" as an environment variable "Genratedtoken"

  Scenario: User Authorization
    Given User object is prepared with already created username and password
    When I send a POST request to "/Account/v1/Authorized"
    Then The response status code should be 200