@companies-api @regression
Feature: Company Management API

  Background:
    Given the FakerAPI is available
    And set base URL to "https://fakerapi.it"

  @important @positive @TC_COMPANY_01
  Scenario: Get companies with default quantity
    When send GET request to endpoint COMPANIES
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify list "data" has 10 items

  @positive @TC_COMPANY_02
  Scenario Outline: Get companies with various quantities
    When send GET request to endpoint COMPANIES with query parameter "_quantity" as "<quantity>"
    Then verify status code is "SUCCESS"
    And verify list "data" has <quantity> items

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 20       |

  @important @positive @TC_COMPANY_03
  Scenario: Verify company data structure
    When send GET request to endpoint COMPANIES with query parameter "_quantity" as "1"
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify first company has field "id"
    And verify first company has field "name"
    And verify first company has field "email"
    And verify first company has field "vat"
    And verify first company has field "website"
    And verify first company has field "country"
    And verify first company has field "addresses"
    And verify first company has field "contact"

  @positive @TC_COMPANY_04
  Scenario: Verify all companies have required fields
    When send GET request to endpoint COMPANIES with query parameter "_quantity" as "5"
    Then verify status code is "SUCCESS"
    And verify all companies have valid email format
    And verify all companies have field "vat"
    And verify all companies have field "country"

  @negative @TC_COMPANY_05
  Scenario: Verify maximum quantity limit is capped at 1000
    When send GET request to endpoint COMPANIES with query parameter "_quantity" as "10000"
    Then verify status code is "SUCCESS"
    And verify list "data" has maximum 1000 items

  @negative @TC_COMPANY_06
  Scenario: Verify API accepts any seed value
    When send GET request to endpoint COMPANIES with query parameter "_seed" as "randomseed12345"
    Then verify status code is "SUCCESS"
    And verify response contains field "seed"
    And verify field "seed" equals "randomseed12345"

  @negative @TC_COMPANY_07
  Scenario: Invalid endpoint returns 404
    When send GET request to endpoint path "/api/v2/invalid"
    Then verify status code is "NOT_FOUND"
