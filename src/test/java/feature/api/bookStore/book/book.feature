Feature: Book Management

  Scenario: Get all books
    When I send a GET request to "/BookStore/v1/Books"
    Then The response status code should be 200
    And set the isbn values from the response body to environment variables

  Scenario: Get book by isbn
    When I send a GET request to "/BookStore/v1/Book" with ISBN
    Then The response status code should be 200

  Scenario: Add books to user collection
    When I send a POST request to "/BookStore/v1/Books" with userId and collection of books
    Then The response status code should be 201

  Scenario: Get user details
    When I send a GET request to "/Account/v1/User/" with userId
    Then The response status code should be 200