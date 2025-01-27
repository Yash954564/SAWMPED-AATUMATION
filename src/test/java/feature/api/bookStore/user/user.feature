Feature: User Management API

  Scenario: Create a new user
    Given a user payload is created with "<userName>" and "<password>"
    When the user create account api is called
    Then the response status code should be "<create_status_code>"
    And the response should contain the key "<create_key>"

  Scenario: Login with existing user
    Given a user payload is created with "<userName>" and "<password>"
    When the user login api is called
    Then the response should contain the key "<login_key>"

  Scenario: Authenticate user
    Given a user payload is created with "<userName>" and "<password>"
    When the user auth api is called
    Then the response status code should be "<auth_status_code>"

  Scenario: Delete existing user
    Given a user payload is created with "<userName>" and "<password>"
    When the user create account api is called
    And the user login api is called
    When the user delete account api is called
    Then the response status code should be "<delete_status_code>"