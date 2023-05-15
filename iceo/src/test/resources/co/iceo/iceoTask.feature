Feature: The latest exchange rate

  Scenario Outline: Get the latest exchange rate for '<baseCurrency>'
    Given As a valid user I want to get latest exchange rate for '<baseCurrency>'
    When I send a request
    Then Response status code is 200
    * Response contains field "base" with value '<baseCurrency>'
    * Response contains a list of currency rates

    Examples: 
      | baseCurrency |
      | USD          |
      | EUR          |
      | PLN          |

  Scenario Outline: Get latest exchange rate for specific pairs
    Given As a valid user I want to get latest exchange rate between base '<baseCurrency>' and '<desiredCurrencies>'
    When I send a request
    Then Response status code is 200
    * Response contains field "base" with value '<baseCurrency>'
    * Response contains a list of currency rates limeted only to desired

    Examples: 
      | baseCurrency | desiredCurrencies | exchangeRates             |
      | EUR          | USD               | EUR/USD                   |
      | PLN          | CZK               | PLN/CZK                   |
      | USD          | JPY,GBP,EUR       | USD/JPY, USD/GBP, USD/EUR |
      | -----        | USD               | EUR/USD                   |

  Scenario Outline: Handle request for invalid currencies
    Given As a valid user I want to get latest exchange rate between base '<baseCurrency>' and '<desiredCurrencies>'
    When I send a request
    Then Response status code is 400
    * Response contains error code '<code_message>'

    Examples: 
      | baseCurrency   | desiredCurrencies | code_message           |
      | NOT_A_CURRENCY | USD               | invalid_base_currency  |
      | EUR            | NOT_A_CURRENCY    | invalid_currency_codes |

  Scenario Outline: Handle request for invalid currencies
    Given As a "<invalidUser>" I want to get latest exchange rate for USD
    When I send a request
    Then Response status code is 401
    * Response contains field "message" with value '<message>'

    Examples: 
      | invalidUser | message                            |
      | NO_AUTH     | No API key found in request        |
      | WRONG_AUTH  | Invalid authentication credentials |

  Scenario: Handle wrong resource request
    Given As a valid user I want to get latest exchange rate for "USD"
    When I send a request to non-existent endpoint
    Then Response status code is 404
    * Response contains field "message" with value "no Route matched with those values"
