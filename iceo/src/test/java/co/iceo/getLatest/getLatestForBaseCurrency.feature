Feature: The latest exchange rate

  Scenario Outline: Get the latest exchange rate for "<baseCurrency>"
    Given As a valid user I want to get latest exchange rate for "<baseCurrency>"
    When I send a request
    Then Response status code is 200
    * Response contains field "base" with value "<baseCurrency>"
    * Response contains a list of currency rates

    Examples: 
      | baseCurrency |
      | USD          |
      | EUR          |
      | PLN          |