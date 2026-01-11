@users-api @regression
Feature: User Management API

  Background:
    Given the FakerAPI is available
    And set base URL to "https://fakerapi.it"

  @important @positive @TC_USER_01
  Scenario: Get users with default quantity
    When send GET request to endpoint USERS
    Then verify status code is "SUCCESS"
    And verify field "status" equals "OK"
    And verify response contains field "data"
    And verify list "data" has 10 items

  @positive @TC_USER_02
  Scenario Outline: Get users with various quantities
    When send GET request to endpoint USERS with query parameter "_quantity" as "<quantity>"
    Then verify status code is "SUCCESS"
    And verify list "data" has <quantity> items

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 1000     |

  @positive @TC_USER_03
  Scenario Outline: Get users with various locales
    When send GET request to endpoint USERS with query parameters:
      | _quantity | 5       |
      | _locale   | <locale> |
    Then verify status code is "SUCCESS"
    And verify field "locale" equals "<locale>"

    Examples:
      | locale   |
      | id_ID    |
      | en_US    |
      | fr_FR    |
      | de_DE    |
      | es_ES    |

  @positive @TC_USER_04
  Scenario: Get users with seed parameter for consistency
    When send GET request to endpoint USERS with query parameters:
      | _quantity | 3   |
      | _seed     | 999 |
    Then verify status code is "SUCCESS"
    And verify response contains field "seed"
    And verify first user has field "id"
    And verify first user has field "email"

  @important @positive @TC_USER_05
  Scenario: Verify user data structure
    When send GET request to endpoint USERS with query parameter "_quantity" as "1"
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify first user has field "id"
    And verify first user has field "uuid"
    And verify first user has field "firstname"
    And verify first user has field "lastname"
    And verify first user has field "email"
    And verify first user has field "username"
    And verify first user has field "password"
    And verify first user has field "ip"
    And verify first user has field "macAddress"
    And verify first user has field "website"
    And verify first user has field "image"

  @positive @TC_USER_06
  Scenario: Verify all users have valid data formats
    When send GET request to endpoint USERS with query parameter "_quantity" as "5"
    Then verify status code is "SUCCESS"
    And verify all users have valid email format
    And verify all users have valid UUID format
    And verify all users have valid IP address format
    And verify all users have valid MAC address format
    And verify all users have valid website URL format
