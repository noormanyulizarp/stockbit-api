@texts-api @regression
Feature: Text Content Management API

  Background:
    Given the FakerAPI is available
    And set base URL to "https://fakerapi.it"

  @important @positive @TC_TEXT_01
  Scenario: Get texts with default quantity
    When send GET request to endpoint TEXTS
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify list "data" has 10 items

  @positive @TC_TEXT_02
  Scenario Outline: Get texts with various quantities
    When send GET request to endpoint TEXTS with query parameter "_quantity" as "<quantity>"
    Then verify status code is "SUCCESS"
    And verify list "data" has <quantity> items

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 20       |

  @important @positive @TC_TEXT_03
  Scenario: Verify text data structure and required fields
    When send GET request to endpoint TEXTS with query parameter "_quantity" as "1"
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify first item has field "title"
    And verify first item has field "author"
    And verify first item has field "genre"
    And verify first item has field "content"

  @negative @TC_TEXT_04
  Scenario: Verify maximum quantity limit is capped at 1000
    When send GET request to endpoint TEXTS with query parameter "_quantity" as "3000"
    Then verify status code is "SUCCESS"
    And verify list "data" has maximum 1000 items

  @negative @TC_TEXT_05
  Scenario: Verify default quantity for string quantity
    When send GET request to endpoint TEXTS with query parameter "_quantity" as "text"
    Then verify status code is "SUCCESS"
    And verify list "data" has 10 items

  @negative @TC_TEXT_06
  Scenario: Invalid endpoint returns 404
    When send GET request to endpoint path "/api/v2/invalid"
    Then verify status code is "NOT_FOUND"
