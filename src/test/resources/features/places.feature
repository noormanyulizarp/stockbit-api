@places-api @regression
Feature: Place Management API

  Background:
    Given the FakerAPI is available
    And set base URL to "https://fakerapi.it"

  @important @positive @TC_PLACE_01
  Scenario: Get places with default quantity
    When send GET request to endpoint PLACES
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify list "data" has 10 items

  @positive @TC_PLACE_02
  Scenario Outline: Get places with various quantities
    When send GET request to endpoint PLACES with query parameter "_quantity" as "<quantity>"
    Then verify status code is "SUCCESS"
    And verify list "data" has <quantity> items

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 20       |

  @important @positive @TC_PLACE_03
  Scenario: Verify place data structure and coordinates
    When send GET request to endpoint PLACES with query parameter "_quantity" as "1"
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify first item has field "latitude"
    And verify first item has field "longitude"

  @positive @TC_PLACE_04
  Scenario: Verify all places have valid coordinates
    When send GET request to endpoint PLACES with query parameter "_quantity" as "5"
    Then verify status code is "SUCCESS"
    And verify all items have field "latitude"
    And verify all items have field "longitude"
    And verify all items have valid coordinates

  @negative @TC_PLACE_05
  Scenario: Verify maximum quantity limit is capped at 1000
    When send GET request to endpoint PLACES with query parameter "_quantity" as "2000"
    Then verify status code is "SUCCESS"
    And verify list "data" has maximum 1000 items

  @negative @TC_PLACE_06
  Scenario: Verify default quantity for zero quantity
    When send GET request to endpoint PLACES with query parameter "_quantity" as "-1"
    Then verify status code is "SUCCESS"
    And verify list "data" has 10 items

  @negative @TC_PLACE_07
  Scenario: Invalid endpoint returns 404
    When send GET request to endpoint path "/api/v2/invalid"
    Then verify status code is "NOT_FOUND"
