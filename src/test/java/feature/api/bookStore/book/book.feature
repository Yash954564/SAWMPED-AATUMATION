Feature: Book Management API

  Scenario: Get all books
    When the get all books api is called
    Then the response status code should be "<getallbooks_status_code>"
    And the response should contain list of books

  Scenario: Get a book by isbn
    Given a book isbn is fetched
    When the get book by isbn api is called
    Then the response status code should be "<getbookbyisbn_status_code>"
    And the response should contain the book with isbn

  Scenario: Add books to user
    Given a user payload is created with "<userName>" and "<password>"
    When the user create account api is called
    And the user login api is called
    And a book isbn is fetched
    When the add books to user api is called
    Then the response status code should be "<addbook_status_code>"

  Scenario: Get user details
    Given a user payload is created with "<userName>" and "<password>"
    When the user create account api is called
    And the user login api is called
    When the get user api is called
    Then the response status code should be "<getuser_status_code>"
    And the response should contain user details

  Scenario: Delete all books for a user
    Given a user payload is created with "<userName>" and "<password>"
    When the user create account api is called
    And the user login api is called
    And a book isbn is fetched
    And the add books to user api is called
    When the delete all books for a user api is called
    Then the response status code should be "<deletebook_status_code>"