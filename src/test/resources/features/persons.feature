@persons-api @regression
Feature: Person Management API

  Background:
    Given the FakerAPI is available
    And set base URL to "https://fakerapi.it"

  @important @positive @TC_PERSON_01
  Scenario: Get persons with default quantity
    When send GET request to endpoint PERSONS
    Then verify status code is "SUCCESS"
    And the persons API response should be successful
    And verify list "data" has 10 items
    And the first person's ID should not be null

  @positive @TC_PERSON_02
  Scenario Outline: Get persons by gender
    When send GET request to endpoint PERSONS with query parameter "_gender" as "<gender>"
    Then verify status code is "SUCCESS"
    And the persons API response should be successful
    And verify list "data" has 10 items
    And all persons should have gender "<gender>"

    Examples:
      | gender |
      | female |
      | male   |

  @positive @TC_PERSON_03
  Scenario: Get persons with birthday range
    When send GET request to endpoint PERSONS with query parameters:
      | _quantity       | 5          |
      | _birthday_start | 1990-01-01 |
      | _birthday_end   | 2000-12-31 |
    Then verify status code is "SUCCESS"
    And the persons API response should be successful
    And verify all persons have birthday between 1990 and 2000

  @important @positive @TC_PERSON_04
  Scenario: Verify person data structure
    When send GET request to endpoint PERSONS with query parameter "_quantity" as "1"
    Then verify status code is "SUCCESS"
    And the persons API response should be successful
    And the first person's first name should not be empty
    And the first person's last name should not be empty
    And the first person's email should be valid
    And the first person's phone should not be empty
    And the first person's gender should not be null
    And the first person's birthday should be in valid format
    And the first person's address should not be null

  @positive @TC_PERSON_05
  Scenario: Verify person address data
    When send GET request to endpoint PERSONS with query parameter "_quantity" as "1"
    Then verify status code is "SUCCESS"
    And the persons API response should be successful
    And the first person's address should not be null
    And verify person address has field "street"
    And verify person address has field "city"
    And verify person address has field "country"
    And verify person address has field "zipcode"

  @positive @TC_PERSON_06
  Scenario: Verify all persons have valid data formats
    When send GET request to endpoint PERSONS with query parameter "_quantity" as "5"
    Then verify status code is "SUCCESS"
    And the persons API response should be successful
    And verify all persons have valid birthday format
    And verify all persons have valid email format
    And verify all persons have valid phone format

  @positive @TC_PERSON_07
  Scenario Outline: Get persons with birthday boundary
    When send GET request to endpoint PERSONS with query parameters:
      | _quantity       | 5            |
      | _birthday_start | <start_date> |
    Then verify status code is "SUCCESS"
    And the persons API response should be successful
    And verify list "data" has 5 items

    Examples:
      | start_date |
      | 1995-01-01 |
      | 2000-12-31 |

  @positive @TC_PERSON_08
  Scenario: Verify all persons have required fields
    When send GET request to endpoint PERSONS with query parameter "_quantity" as "5"
    Then verify status code is "SUCCESS"
    And the persons API response should be successful
    And verify all persons have field "website"
    And verify all persons have field "image"
