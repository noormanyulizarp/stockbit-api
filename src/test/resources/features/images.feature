@images-api @regression
Feature: Image Management API

  Background:
    Given the FakerAPI is available
    And set base URL to "https://fakerapi.it"

  @important @positive @TC_IMAGE_01
  Scenario: Get images with default quantity
    When send GET request to endpoint IMAGES
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify list "data" has 10 items

  @positive @TC_IMAGE_02
  Scenario Outline: Get images with various quantities
    When send GET request to endpoint IMAGES with query parameter "_quantity" as "<quantity>"
    Then verify status code is "SUCCESS"
    And verify list "data" has <quantity> items

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 20       |

  @important @positive @TC_IMAGE_03
  Scenario: Verify image data structure and required fields
    When send GET request to endpoint IMAGES with query parameter "_quantity" as "1"
    Then verify status code is "SUCCESS"
    And verify response contains field "data"
    And verify first item has field "title"
    And verify first item has field "description"
    And verify first item has field "url"

  @positive @TC_IMAGE_04
  Scenario: Verify all images have required fields
    When send GET request to endpoint IMAGES with query parameter "_quantity" as "5"
    Then verify status code is "SUCCESS"
    And verify all items have field "url"
    And verify all items have field "title"
    And verify all items have field "description"
