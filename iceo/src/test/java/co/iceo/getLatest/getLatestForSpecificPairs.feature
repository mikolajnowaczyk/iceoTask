Feature: The latest exchange rate

  Scenario Outline: Get latest exchange rate for specific pairs
    Given As a valid user I want to get latest exchange rate between base "<baseCurrency>" and "<desiredCurrencies>"
    When I send a request
    Then Response status code is 200
    * Response contains field "base" with value "<baseCurrency>"
    * Response contains a list of currency rates limeted only to desired

    Examples: 
      | baseCurrency | desiredCurrencies | exchangeRates             |
      | EUR          | USD               | EUR/USD                   |
      | PLN          | CZK               | PLN/CZK                   |
      | USD          | JPY,GBP,EUR       | USD/JPY, USD/GBP, USD/EUR |
      | -----        | USD               | EUR/USD                   |
