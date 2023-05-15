Feature: The latest exchange rate

  Scenario: Handle wrong resource request
    Given As a valid user I want to get latest exchange rate for "USD"
    When I send a request to non-existent endpoint
    Then Response status code is 404
    * Response contains field "message" with value "no Route matched with those values"
