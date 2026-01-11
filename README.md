# StockBit API Automation Framework

[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-red.svg)](https://maven.apache.org/)
[![Gradle](https://img.shields.io/badge/Gradle-8.5%2B-blue.svg)](https://gradle.org/)
[![Cucumber](https://img.shields.io/badge/Cucumber-7.14.0-brightgreen.svg)](https://cucumber.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.9.0-important.svg)](https://testng.org/)
[![RestAssured](https://img.shields.io/badge/RestAssured-5.3.2-blue.svg)](https://rest-assured.io/)
[![Failsafe](https://img.shields.io/badge/Failsafe-3.3.2-brightgreen.svg)](https://failsafe.dev/)

API automation framework built with **Java**, **Cucumber BDD**, **TestNG**, **RestAssured**, and **Failsafe** for testing the [FakerAPI.it](https://fakerapi.it) endpoints. Supports both **Maven** and **Gradle** build systems.

## Key Features

- **BDD Approach**: Tests written in Gherkin syntax using Cucumber
- **Dual Build Support**: Works with Maven and Gradle
- **Service Layer Pattern**: Clean separation between steps and API calls (all calls through service layer)
- **POJO Pattern**: Type-safe response handling with explicit POJO classes
- **Enum Standardization**: Consistent use of enums for endpoints, status codes, and locales
- **Negative Testing**: 404 error testing for invalid endpoints
- **Auto Retry**: Failsafe-based retry mechanism for HTTP 429 rate limits
- **Sequential Execution**: No parallel tests to avoid API rate limiting

## Test Results

| Build Tool | Tests | Failures | Errors | Status |
|------------|-------|----------|--------|--------|
| **Maven** | 110 | 0 | 0 | ✅ PASSED |
| **Gradle** | 110 | 0 | 0 | ✅ PASSED |

## Project Structure

```
stockbit-api/
├── pom.xml                                    # Maven configuration
├── build.gradle                               # Gradle configuration
├── gradlew.bat                                # Gradle wrapper (Windows)
├── src/
│   └── test/
│       ├── java/com/stockbit/api/
│       │   ├── api/
│       │   │   ├── common/
│       │   │   │   └── ApiResponse.java       # Generic API response wrapper
│       │   │   └── response/                  # POJO classes for each endpoint
│       │   │       ├── UserResponsePayload.java
│       │   │       ├── ProductResponsePayload.java
│       │   │       ├── PersonResponsePayload.java
│       │   │       └── ...
│       │   ├── config/
│       │   │   └── Config.java                # Configuration management
│       │   ├── context/
│       │   │   └── TestContext.java           # Shared state (Singleton)
│       │   ├── enums/
│       │   │   ├── Endpoint.java              # Endpoint enum with paths
│       │   │   ├── StatusCode.java            # HTTP status code enum
│       │   │   └── Locale.java                # Locale enum
│       │   ├── runner/
│       │   │   └── TestNGCucumberRunner.java  # TestNG Cucumber runner
│       │   ├── services/                      # Service layer for API calls
│       │   │   ├── CommonService.java         # Generic service for raw paths
│       │   │   ├── UserService.java
│       │   │   ├── ProductService.java
│       │   │   ├── PersonService.java
│       │   │   └── ...
│       │   ├── steps/
│       │   │   ├── shared/
│       │   │   │   └── CommonSteps.java       # Generic request & verification steps
│       │   │   └── validation/                # Validation step classes
│       │   │       ├── CommonValidationSteps.java
│       │   │       ├── PersonsValidationSteps.java
│       │   │       └── ...
│       │   └── utils/
│       │       └── RetryHelper.java           # Retry mechanism with Failsafe
│       └── resources/
│           └── features/                      # Cucumber feature files
│               ├── users.feature
│               ├── products.feature
│               ├── persons.feature
│               └── ...
└── target/                                     # Build output (Maven)
```

## Architecture

### Layer Pattern

**Service Layer** (`services/`):
- Handles all HTTP requests using RestAssured
- Returns type-safe POJO responses
- Manages endpoint paths via Endpoint enum
- Implements retry logic via RetryHelper
- **CommonService**: Handles raw paths for negative testing (404 errors)

**Steps Layer** (`steps/`):
- **CommonSteps** (shared/): Generic request handling for all endpoints
- **ValidationSteps** (validation/): Business logic validations per endpoint

**POJO Layer** (`api/response/`):
- Type-safe response objects with Jackson annotations
- Explicit field mapping with @JsonProperty
- Handles unknown properties gracefully

### Key Components

**CommonSteps.java** - Generic request & verification steps:
```gherkin
When send GET request to endpoint USERS
When send GET request to endpoint PERSONS with query parameter "_gender" as "female"
When send GET request to endpoint path "/api/v2/invalid"
Then verify status code is "SUCCESS"
Then verify status code is "NOT_FOUND"
And verify list "data" has 10 items
```

**CommonService.java** - Service layer for raw paths:
```java
// For negative tests (404 errors)
ApiResponse<Object> response = CommonService.getRawEndpoint("/api/v2/invalid");
```

**PersonsValidationSteps.java** - POJO-based validations:
```gherkin
And the first person's email should be valid
And verify all persons have valid birthday format
And verify person address has field "street"
```

## Prerequisites

- **Java JDK 11** or higher
- **Maven 3.6+** OR **Gradle 8.5+**
- **IDE**: IntelliJ IDEA (recommended) with Cucumber plugin

## Running Tests

### Quick Start

```bash
# Maven
mvn clean test

# Gradle (Windows)
./gradlew.bat clean test

# Gradle (Linux/Mac)
./gradlew clean test
```

### Using Maven

```bash
# Run all tests
mvn clean test

# Run by tag
mvn test -Dcucumber.options="--tags @important"
mvn test -Dcucumber.options="--tags @users-api"

# Run with TestNG
mvn test -Dtest=TestNGCucumberRunner
```

### Using Gradle

```bash
# Run all tests
./gradlew.bat clean test                    # Windows
./gradlew clean test                        # Linux/Mac

# Run by tag
./gradlew.bat test -Dcucumber.options="--tags @important"

# Run with TestNG
./gradlew.bat test --tests TestNGCucumberRunner
```

### Run from IDE

Right-click on `TestNGCucumberRunner.java` → Run

## Allure Reports

### Important: Reports Must Be Served

⚠️ **Do NOT open `index.html` directly** - Allure reports must be served through a web server due to browser CORS security restrictions. Opening the HTML file directly will cause the report to stuck in "loading" state.

### Using Maven

```bash
# Serve report (recommended) - Starts web server & opens browser
mvn allure:serve

# Generate static report only (for archival)
mvn allure:report
```

### Using Gradle

```bash
# Serve report (recommended) - Starts web server & opens browser
./gradlew.bat allureServe    # Windows
./gradlew allureServe        # Linux/Mac

# Generate static report only (for archival)
./gradlew.bat allureReport   # Windows
./gradlew allureReport       # Linux/Mac
```

### What Happens When You Serve

When you run `allure:serve`:
1. Allure downloads/verifies the Allure binary (2.24.0)
2. Generates report from `target/allure-results` (Maven) or `build/allure-results` (Gradle)
3. Starts a local Jetty web server
4. Opens browser automatically (e.g., `http://192.168.100.113:55076/`)
5. Press `Ctrl+C` to stop the server

### Static Report Location

For archival or CI/CD integration:
- Maven: `target/site/allure-maven-plugin/index.html`
- Gradle: `build/reports/allure-report/allureReport/index.html`

**Note:** Static reports cannot be opened directly - they must be hosted on a web server or viewed through `allure:serve`.

## Test Coverage

| Metric | Count |
|--------|-------|
| **Total Feature Files** | 10 |
| **Total Test Scenarios** | 83 |
| **Total Tests** | 110 |
| **Important Tests** | 20 |
| **Positive Tests** | 50 |
| **Negative Tests** | 30 |

### Negative Testing Strategy

Each endpoint includes comprehensive negative tests based on manual API exploration:

1. **Maximum Quantity Limit** - Verifies API caps at 1000 items even when requesting more
   ```gherkin
   When send GET request to endpoint USERS with query parameter "_quantity" as "1500"
   Then verify list "data" has maximum 1000 items
   ```

2. **Default Quantity for Invalid Input** - Verifies API returns 10 items for invalid quantities (0, negative, strings)
   ```gherkin
   When send GET request to endpoint USERS with query parameter "_quantity" as "0"
   Then verify list "data" has 10 items
   ```

3. **Invalid Endpoint** - Verifies 404 response for non-existent endpoints
   ```gherkin
   When send GET request to endpoint path "/api/v2/invalid"
   Then verify status code is "NOT_FOUND"
   ```

### Feature Files

| Feature | Tag | Scenarios | Negative Tests |
|---------|-----|-----------|----------------|
| users.feature | @users-api | 10 | 3 (max qty, default qty, 404) |
| products.feature | @products-api | 9 | 3 (max qty, default qty, 404) |
| persons.feature | @persons-api | 12 | 3 (max qty, gender param, 404) |
| companies.feature | @companies-api | 8 | 3 (max qty, seed param, 404) |
| books.feature | @books-api | 8 | 3 (max qty, default qty, 404) |
| credit-cards.feature | @creditcards-api | 8 | 3 (max qty, default qty, 404) |
| images.feature | @images-api | 8 | 3 (max qty, default qty, 404) |
| texts.feature | @texts-api | 7 | 3 (max qty, default qty, 404) |
| places.feature | @places-api | 8 | 3 (max qty, default qty, 404) |
| addresses.feature | @addresses-api | 8 | 3 (max qty, default qty, 404) |

### API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/v2/users` | GET | User data with quantity, locale, seed params |
| `/api/v2/products` | GET | Product data with categories, tags |
| `/api/v2/persons` | GET | Person data with gender, birthday range |
| `/api/v2/companies` | GET | Company data with structure validation |
| `/api/v2/books` | GET | Book data with ISBN validation |
| `/api/v2/creditCards` | GET | Credit card data with validation |
| `/api/v2/images` | GET | Image metadata with dimensions |
| `/api/v2/texts` | GET | Text content with author, genre |
| `/api/v2/places` | GET | Place data with coordinates |
| `/api/v2/addresses` | GET | Address data with location |
| `/api/v2/*` | GET | Invalid paths return 404 |

## Enum Standardization

All feature files use standardized enums for consistency:

### Endpoint Enum (without quotes)
```gherkin
When send GET request to endpoint USERS
When send GET request to endpoint PERSONS
```

### StatusCode Enum (with quotes)
```gherkin
Then verify status code is "SUCCESS"
Then verify status code is "NOT_FOUND"
```

### Locale Enum (with quotes)
```gherkin
When send GET request to endpoint USERS with query parameter "_locale" as "INDONESIAN"
When send GET request to endpoint USERS with query parameter "_locale" as "ENGLISH_US"
```

### Negative Test Pattern
```gherkin
When send GET request to endpoint path "/api/v2/invalid"
Then verify status code is "NOT_FOUND"
```

## Design Patterns

| Pattern | Purpose | Location |
|---------|---------|----------|
| **Service Layer Pattern** | Abstraction between steps and API calls | `services/` |
| **POJO Pattern** | Type-safe response handling | `api/response/` |
| **Singleton Pattern** | Shared state management | `TestContext.java` |
| **Enum Pattern** | Standardized endpoint/status codes | `enums/` |
| **Retry Pattern** | Handle rate limits automatically | `RetryHelper.java` |

## Code Conventions

- **No wildcard imports** - All imports are explicit
- **Type-safe** - Use POJOs instead of Map
- **Descriptive names** - Self-explanatory method names
- **Consistent enums** - Use enums for endpoints, status codes, locales
- **Service layer first** - All API calls go through service layer

## Retry Mechanism

Framework uses **Failsafe** library to automatically retry requests that fail with HTTP 429:

```java
// Automatic retry in Service classes
Response response = RetryHelper.executeWithRetry(() ->
    getRequestSpecification().when().get(endpoint)
);

// Retry Policy:
// - Max retries: 6
// - Delay: 2 seconds between retries
```

## Extending the Framework

### Adding New Endpoint

1. Create POJO in `api/response/`
2. Create Service in `services/`
3. Add Endpoint enum value
4. Create feature file using enum pattern
5. Add negative test case for 404

### Example

```java
// 1. POJO
@JsonProperty("id")
private Integer id;

// 2. Service
public static ApiResponse<MyResponsePayload> getData() {
    Response response = RetryHelper.executeWithRetry(() ->
        getRequestSpecification().when().get(Endpoint.MY_ENDPOINT.getFullPath())
    );
    return parseResponse(response);
}

// 3. Feature
When send GET request to endpoint MY_ENDPOINT
Then verify status code is "SUCCESS"

// 4. Negative Test
When send GET request to endpoint path "/api/v2/invalid"
Then verify status code is "NOT_FOUND"
```
