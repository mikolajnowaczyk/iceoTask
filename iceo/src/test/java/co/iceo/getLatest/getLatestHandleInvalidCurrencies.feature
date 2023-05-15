Feature: The latest exchange rate

  Scenario Outline: Handle request for invalid currencies
    Given As a valid user I want to get latest exchange rate between base "<baseCurrency>" and "<desiredCurrencies>"
    When I send a request
    Then Response status code is 400
    * Response contains error code "<code_message>"

    Examples: 
      | baseCurrency   | desiredCurrencies | code_message           |
      | NOT_A_CURRENCY | USD               | invalid_base_currency  |
      | EUR            | NOT_A_CURRENCY    | invalid_currency_codes |
