@books-api @regression
Feature: Book Management API

  Background:
    Given the FakerAPI is available
    And set base URL to "https://fakerapi.it"

  @important @positive @TC_BOOK_01
  Scenario: Get books with default quantity
    When send GET request to endpoint BOOKS
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify list "data" has 10 items

  @positive @TC_BOOK_02
  Scenario Outline: Get books with various quantities
    When send GET request to endpoint BOOKS with query parameter "_quantity" as "<quantity>"
    Then verify status code is "SUCCESS"
    And verify list "data" has <quantity> items

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 20       |

  @important @positive @TC_BOOK_03
  Scenario: Verify book data structure
    When send GET request to endpoint BOOKS with query parameter "_quantity" as "1"
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify first item has field "id"
    And verify first item has field "title"
    And verify first item has field "author"
    And verify first item has field "genre"
    And verify first item has field "description"
    And verify first item has field "isbn"
    And verify first item has field "image"
    And verify first item has field "published"
    And verify first item has field "publisher"

  @positive @TC_BOOK_04
  Scenario: Verify all books have required fields
    When send GET request to endpoint BOOKS with query parameter "_quantity" as "5"
    Then verify status code is "SUCCESS"
    And verify all items have field "isbn"
    And verify all items have field "title"
    And verify all items have field "author"
    And verify all items have field "genre"
    And verify all items have field "published"

  @negative @TC_BOOK_05
  Scenario: Verify maximum quantity limit is capped at 1000
    When send GET request to endpoint BOOKS with query parameter "_quantity" as "5000"
    Then verify status code is "SUCCESS"
    And verify list "data" has maximum 1000 items

  @negative @TC_BOOK_06
  Scenario: Verify default quantity returned for invalid quantity
    When send GET request to endpoint BOOKS with query parameter "_quantity" as "abc"
    Then verify status code is "SUCCESS"
    And verify list "data" has 10 items

  @negative @TC_BOOK_07
  Scenario: Invalid endpoint returns 404
    When send GET request to endpoint path "/api/v2/invalid"
    Then verify status code is "NOT_FOUND"
