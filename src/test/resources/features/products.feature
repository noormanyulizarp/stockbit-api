@products-api @regression
Feature: Product Management API

  Background:
    Given the FakerAPI is available
    And set base URL to "https://fakerapi.it"

  @important @positive @TC_PRODUCT_01
  Scenario: Get products with default quantity
    When send GET request to endpoint PRODUCTS
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify list "data" has 10 items

  @positive @TC_PRODUCT_02
  Scenario Outline: Get products with various quantities
    When send GET request to endpoint PRODUCTS with query parameter "_quantity" as "<quantity>"
    Then verify status code is "SUCCESS"
    And verify list "data" has <quantity> items

    Examples:
      | quantity |
      | 1        |
      | 3        |
      | 5        |

  @positive @TC_PRODUCT_03
  Scenario: Get products with price range and tax parameters
    When send GET request to endpoint PRODUCTS with query parameters:
      | _quantity   | 5    |
      | _price_min  | 100  |
      | _price_max  | 500  |
      | _taxes      | 10   |
    Then verify status code is "SUCCESS"
    And verify response contains field "data"

  @important @positive @TC_PRODUCT_04
  Scenario: Verify product data structure
    When send GET request to endpoint PRODUCTS with query parameter "_quantity" as "1"
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify first product has field "id"
    And verify first product has field "name"
    And verify first product has field "price"
    And verify first product has field "net_price"
    And verify first product has field "categories"
    And verify first product has field "tags"
    And verify first product has field "images"

  @positive @TC_PRODUCT_05
  Scenario: Verify all products have required fields
    When send GET request to endpoint PRODUCTS with query parameter "_quantity" as "5"
    Then verify status code is "SUCCESS"
    And verify all products have field "ean"
    And verify all products have field "upc"
    And verify all products have field "description"

  @negative @TC_PRODUCT_06
  Scenario: Verify maximum quantity limit is capped at 1000
    When send GET request to endpoint PRODUCTS with query parameter "_quantity" as "2000"
    Then verify status code is "SUCCESS"
    And verify list "data" has maximum 1000 items

  @negative @TC_PRODUCT_07
  Scenario: Verify default quantity returned for invalid quantity
    When send GET request to endpoint PRODUCTS with query parameter "_quantity" as "-5"
    Then verify status code is "SUCCESS"
    And verify list "data" has 10 items

  @negative @TC_PRODUCT_08
  Scenario: Invalid endpoint returns 404
    When send GET request to endpoint path "/api/v2/invalid"
    Then verify status code is "NOT_FOUND"
