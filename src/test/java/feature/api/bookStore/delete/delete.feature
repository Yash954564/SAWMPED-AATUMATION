Feature: Delete Operations

  Scenario: Delete all books from user collection
    When I send a DELETE request to "/BookStore/v1/Books" with userId
    Then The response status code should be 204

  Scenario: Delete user account
    When I send a DELETE request to "/Account/v1/User/" with userId
    Then The response status code should be 204