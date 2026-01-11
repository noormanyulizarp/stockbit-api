@addresses-api @regression
Feature: Address Management API

  Background:
    Given the FakerAPI is available
    And set base URL to "https://fakerapi.it"

  @important @positive @TC_ADDRESS_01
  Scenario: Get addresses with default quantity
    When send GET request to endpoint ADDRESSES
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify list "data" has 10 items

  @positive @TC_ADDRESS_02
  Scenario Outline: Get addresses with various quantities
    When send GET request to endpoint ADDRESSES with query parameter "_quantity" as "<quantity>"
    Then verify status code is "SUCCESS"
    And verify list "data" has <quantity> items

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 20       |

  @important @positive @TC_ADDRESS_03
  Scenario: Verify address data structure
    When send GET request to endpoint ADDRESSES with query parameter "_quantity" as "1"
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify first item has field "street"
    And verify first item has field "city"
    And verify first item has field "country"
    And verify first item has field "zipcode"

  @positive @TC_ADDRESS_04
  Scenario: Verify all addresses have required fields and coordinates
    When send GET request to endpoint ADDRESSES with query parameter "_quantity" as "5"
    Then verify status code is "SUCCESS"
    And verify all items have field "street"
    And verify all items have field "city"
    And verify all items have field "country"
    And verify all items have field "zipcode"
    And verify all items have field "latitude"
    And verify all items have field "longitude"

  @negative @TC_ADDRESS_05
  Scenario: Verify maximum quantity limit is capped at 1000
    When send GET request to endpoint ADDRESSES with query parameter "_quantity" as "9999"
    Then verify status code is "SUCCESS"
    And verify list "data" has maximum 1000 items

  @negative @TC_ADDRESS_06
  Scenario: Verify default quantity returned for zero quantity
    When send GET request to endpoint ADDRESSES with query parameter "_quantity" as "0"
    Then verify status code is "SUCCESS"
    And verify list "data" has 10 items

  @negative @TC_ADDRESS_07
  Scenario: Invalid endpoint returns 404
    When send GET request to endpoint path "/api/v2/invalid"
    Then verify status code is "NOT_FOUND"
