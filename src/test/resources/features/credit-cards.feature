@creditcards-api @regression
Feature: Credit Card Management API

  Background:
    Given the FakerAPI is available
    And set base URL to "https://fakerapi.it"

  @important @positive @TC_CREDITCARD_01
  Scenario: Get credit cards with default quantity
    When send GET request to endpoint CREDIT_CARDS
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify list "data" has 10 items

  @positive @TC_CREDITCARD_02
  Scenario Outline: Get credit cards with various quantities
    When send GET request to endpoint CREDIT_CARDS with query parameter "_quantity" as "<quantity>"
    Then verify status code is "SUCCESS"
    And verify list "data" has <quantity> items

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 20       |

  @important @positive @TC_CREDITCARD_03
  Scenario: Verify credit card data structure
    When send GET request to endpoint CREDIT_CARDS with query parameter "_quantity" as "1"
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify first item has field "type"
    And verify first item has field "number"
    And verify first item has field "expiration"
    And verify first item has field "owner"

  @positive @TC_CREDITCARD_04
  Scenario: Verify all credit cards have required fields
    When send GET request to endpoint CREDIT_CARDS with query parameter "_quantity" as "5"
    Then verify status code is "SUCCESS"
    And verify all items have field "number"
    And verify all items have field "expiration"
    And verify all items have field "type"
    And verify all items have field "owner"
