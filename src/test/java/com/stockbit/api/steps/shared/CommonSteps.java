package com.stockbit.api.steps.shared;

import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.api.response.AddressResponsePayload;
import com.stockbit.api.api.response.BookResponsePayload;
import com.stockbit.api.api.response.CompanyResponsePayload;
import com.stockbit.api.api.response.CreditCardResponsePayload;
import com.stockbit.api.api.response.ImageResponsePayload;
import com.stockbit.api.api.response.PersonResponsePayload;
import com.stockbit.api.api.response.PlaceResponsePayload;
import com.stockbit.api.api.response.ProductResponsePayload;
import com.stockbit.api.api.response.TextResponsePayload;
import com.stockbit.api.api.response.UserResponsePayload;
import com.stockbit.api.context.TestContext;
import com.stockbit.api.enums.Endpoint;
import com.stockbit.api.enums.Locale;
import com.stockbit.api.enums.StatusCode;
import com.stockbit.api.services.AddressService;
import com.stockbit.api.services.BookService;
import com.stockbit.api.services.CommonService;
import com.stockbit.api.services.CompanyService;
import com.stockbit.api.services.CreditCardService;
import com.stockbit.api.services.ImageService;
import com.stockbit.api.services.PersonService;
import com.stockbit.api.services.PlaceService;
import com.stockbit.api.services.ProductService;
import com.stockbit.api.services.TextService;
import com.stockbit.api.services.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonSteps {

    private final TestContext context;

    public CommonSteps() {
        this.context = TestContext.getInstance();
    }

    @Given("the FakerAPI is available")
    public void fakerApiIsAvailable() {
    }

    @Given("set base URL to {string}")
    public void setBaseUrl(String url) {
        context.setBaseUrl(url);
    }

    @When("send GET request to endpoint {word}")
    public void sendGetRequestToEndpoint(String endpointName) {
        ApiResponse<?> response = callService(endpointName, 0);
        context.set("lastResponse", response);
    }

    @When("send GET request to endpoint path {string}")
    public void sendGetRequestToEndpointPath(String path) {
        ApiResponse<?> response = CommonService.getRawEndpoint(path);
        context.set("lastResponse", response);
    }

    @When("send GET request to {string} with query parameter {string} as {string}")
    public void sendGetRequestWithQueryParam(String endpoint, String paramKey, String paramValue) {
        String endpointName = extractEndpointName(endpoint);
        ApiResponse<?> response = callServiceWithParam(endpointName, paramKey, paramValue);
        context.set("lastResponse", response);
    }

    @When("send GET request to endpoint {word} with query parameter {string} as {string}")
    public void sendGetRequestToEndpointWithQueryParam(String endpointName, String paramKey, String paramValue) {
        ApiResponse<?> response = callServiceWithParam(endpointName, paramKey, paramValue);
        context.set("lastResponse", response);
    }

    @When("send GET request to {string} with query parameters:")
    public void sendGetRequestWithQueryParams(String endpoint, Map<String, String> params) {
        String endpointName = extractEndpointName(endpoint);
        Map<String, Object> objectParams = new HashMap<>(params);
        ApiResponse<?> response = callServiceWithParams(endpointName, objectParams);
        context.set("lastResponse", response);
    }

    @When("send GET request to endpoint {word} with query parameters:")
    public void sendGetRequestToEndpointWithQueryParams(String endpointName, Map<String, String> params) {
        Map<String, Object> objectParams = new HashMap<>(params);
        ApiResponse<?> response = callServiceWithParams(endpointName, objectParams);
        context.set("lastResponse", response);
    }

    @Then("verify status code is {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        assertEquals(expectedStatusCode, getLastResponseCode());
    }

    @Then("verify status code is {string}")
    public void verifyStatusCodeString(String statusCodeName) {
        StatusCode statusCode = StatusCode.valueOf(statusCodeName.toUpperCase());
        assertEquals(statusCode.getCode(), getLastResponseCode());
    }

    @Then("verify response is successful")
    public void verifyResponseIsSuccessful() {
        ApiResponse<?> response = getLastResponse();
        Assert.assertTrue("API response was not successful", response.isSuccess());
        assertEquals(StatusCode.SUCCESS.getCode(), getLastResponseCode());
    }

    @Then("verify response contains field {string}")
    public void verifyFieldExists(String fieldPath) {
        ApiResponse<?> response = getLastResponse();
        Object value = getFieldValue(response, fieldPath);
        Assert.assertNotNull("Field '" + fieldPath + "' should not be null", value);
    }

    @Then("verify field {string} equals {string}")
    public void verifyFieldEqualsString(String fieldPath, String value) {
        ApiResponse<?> response = getLastResponse();
        Object fieldValue = getFieldValue(response, fieldPath);
        Assert.assertEquals("Field '" + fieldPath + "' mismatch", value, fieldValue);
    }

    @Then("verify list {string} has {int} items")
    public void verifyListSize(String arrayPath, int size) {
        ApiResponse<?> response = getLastResponse();
        List<?> dataList = response.getData();
        Assert.assertEquals("List size mismatch for " + arrayPath, size, dataList.size());
    }

    @Then("verify list {string} has maximum {int} items")
    public void verifyListMaxSize(String arrayPath, int maxSize) {
        ApiResponse<?> response = getLastResponse();
        List<?> dataList = response.getData();
        Assert.assertTrue("List size " + dataList.size() + " exceeds max " + maxSize, dataList.size() <= maxSize);
    }

    // ========== Private Helper Methods ==========

    private String extractEndpointName(String endpoint) {
        if (endpoint.contains("/")) {
            String[] parts = endpoint.split("/");
            String lastPart = parts[parts.length - 1];
            return convertToEndpointName(lastPart);
        }
        return endpoint.toUpperCase();
    }

    private String convertToEndpointName(String path) {
        if (path.equalsIgnoreCase("creditcards")) return "CREDIT_CARDS";
        return path.toUpperCase();
    }

    private ApiResponse<?> callService(String endpointName, int quantity) {
        Endpoint endpoint = Endpoint.valueOf(endpointName.toUpperCase());

        switch (endpoint) {
            case USERS:
                return quantity > 0 ? UserService.getUsers(quantity) : UserService.getUsers();
            case PRODUCTS:
                return quantity > 0 ? ProductService.getProducts(quantity) : ProductService.getProducts();
            case BOOKS:
                return quantity > 0 ? BookService.getBooks(quantity) : BookService.getBooks();
            case ADDRESSES:
                return quantity > 0 ? AddressService.getAddresses(quantity) : AddressService.getAddresses();
            case COMPANIES:
                return quantity > 0 ? CompanyService.getCompanies(quantity) : CompanyService.getCompanies();
            case CREDIT_CARDS:
                return quantity > 0 ? CreditCardService.getCreditCards(quantity) : CreditCardService.getCreditCards();
            case IMAGES:
                return quantity > 0 ? ImageService.getImages(quantity) : ImageService.getImages();
            case TEXTS:
                return quantity > 0 ? TextService.getTexts(quantity) : TextService.getTexts();
            case PLACES:
                return quantity > 0 ? PlaceService.getPlaces(quantity) : PlaceService.getPlaces();
            case PERSONS:
                return PersonService.getPersons(quantity);
            default:
                throw new IllegalArgumentException("Unknown endpoint: " + endpoint);
        }
    }

    private ApiResponse<?> callServiceWithParam(String endpointName, String paramKey, String paramValue) {
        Endpoint endpoint = Endpoint.valueOf(endpointName.toUpperCase());

        switch (endpoint) {
            case USERS:
                if (paramKey.equals("_quantity")) {
                    return UserService.getUsers(Integer.parseInt(paramValue));
                }
                if (paramKey.equals("_locale")) {
                    String locale = getLocaleCodeOrDefault(paramValue);
                    return UserService.getUsers(locale, 10);
                }
                if (paramKey.equals("_seed")) {
                    return UserService.getUsers(10, paramValue);
                }
                // Default - use params map
                Map<String, Object> userParams = new HashMap<>();
                userParams.put(paramKey, paramValue);
                return UserService.getUsers(userParams);
            case PERSONS:
                if (paramKey.equals("_quantity")) {
                    return PersonService.getPersons(Integer.parseInt(paramValue));
                }
                return PersonService.getPersons(paramValue, 10);
            default:
                Map<String, Object> params = new HashMap<>();
                params.put(paramKey, paramValue);
                return callServiceWithParams(endpointName, params);
        }
    }

    private ApiResponse<?> callServiceWithParams(String endpointName, Map<String, Object> params) {
        Endpoint endpoint = Endpoint.valueOf(endpointName.toUpperCase());

        switch (endpoint) {
            case USERS:
                return UserService.getUsers(params);
            case PRODUCTS:
                return ProductService.getProducts(params);
            case BOOKS:
                return BookService.getBooks(params);
            case ADDRESSES:
                return AddressService.getAddresses(params);
            case COMPANIES:
                return CompanyService.getCompanies(params);
            case CREDIT_CARDS:
                return CreditCardService.getCreditCards(params);
            case IMAGES:
                return ImageService.getImages(params);
            case TEXTS:
                return TextService.getTexts(params);
            case PLACES:
                return PlaceService.getPlaces(params);
            case PERSONS:
                return PersonService.getPersons(params);
            default:
                throw new IllegalArgumentException("Unknown endpoint: " + endpoint);
        }
    }

    private ApiResponse<?> getLastResponse() {
        return context.get("lastResponse", ApiResponse.class);
    }

    private Integer getLastResponseCode() {
        ApiResponse<?> response = getLastResponse();
        Integer code = response.getHttpStatusCode();
        if (code == null) {
            throw new AssertionError("HTTP status code is null! Response status: " + response.getStatus() + ", code: " + response.getCode());
        }
        return code;
    }

    private Object getFieldValue(ApiResponse<?> response, String fieldPath) {
        if (fieldPath.equals("status")) return response.getStatus();
        if (fieldPath.equals("data")) return response.getData();
        if (fieldPath.equals("total")) return response.getTotal();
        if (fieldPath.equals("locale")) return response.getLocale();
        if (fieldPath.equals("seed")) return response.getSeed();
        return null;
    }

    private String getLocaleCodeOrDefault(String value) {
        try {
            Locale locale = Locale.valueOf(value);
            return locale.getCode();
        } catch (IllegalArgumentException e) {
            return value;
        }
    }

    private void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }

    private void assertEquals(String expected, String actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError("Expected '" + expected + "' but was '" + actual + "'");
        }
    }
}
