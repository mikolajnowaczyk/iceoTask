Feature: The latest exchange rate

  Scenario Outline: Handle request with invalid authorization
    Given As a "<invalidUser>" I want to get latest exchange rate for USD
    When I send a request
    Then Response status code is 401
    * Response contains field "message" with value '<message>'

    Examples: 
      | invalidUser | message                            |
      | NO_AUTH     | No API key found in request        |
      | WRONG_AUTH  | Invalid authentication credentials |
