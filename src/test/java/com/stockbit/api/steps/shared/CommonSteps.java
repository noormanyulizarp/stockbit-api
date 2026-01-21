package com.stockbit.api.steps.shared;

import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.context.TestContext;
import com.stockbit.api.enums.StatusCode;
import com.stockbit.api.services.CommonService;
import com.stockbit.api.utils.ServiceHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonSteps {

    private final TestContext context;
    private final ServiceHelper serviceHelper;

    public CommonSteps() {
        this.context = TestContext.getInstance();
        this.serviceHelper = new ServiceHelper();
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
        ApiResponse<?> response = serviceHelper.callService(endpointName, 0);
        context.set("lastResponse", response);
    }

    @When("send GET request to endpoint path {string}")
    public void sendGetRequestToEndpointPath(String path) {
        ApiResponse<?> response = CommonService.getRawEndpoint(path);
        context.set("lastResponse", response);
    }

    @When("send GET request to {string} with query parameter {string} as {string}")
    public void sendGetRequestWithQueryParam(String endpoint, String paramKey, String paramValue) {
        String endpointName = serviceHelper.extractEndpointName(endpoint);

        if (endpointName.equals("USERS") && "_quantity".equals(paramKey)) {
            ApiResponse<?> response = serviceHelper.callService(endpointName, Integer.parseInt(paramValue));
            context.set("lastResponse", response);
            return;
        }
        if (endpointName.equals("PERSONS") && "_quantity".equals(paramKey)) {
            ApiResponse<?> response = serviceHelper.callService(endpointName, Integer.parseInt(paramValue));
            context.set("lastResponse", response);
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put(paramKey, paramValue);
        ApiResponse<?> response = serviceHelper.callService(endpointName, params);
        context.set("lastResponse", response);
    }

    @When("send GET request to endpoint {word} with query parameter {string} as {string}")
    public void sendGetRequestToEndpointWithQueryParam(String endpointName, String paramKey, String paramValue) {
        // Special handling for USERS endpoint with _quantity, _locale, _seed
        if (endpointName.equals("USERS") && "_quantity".equals(paramKey)) {
            ApiResponse<?> response = serviceHelper.callService(endpointName, Integer.parseInt(paramValue));
            context.set("lastResponse", response);
            return;
        }
        if (endpointName.equals("PERSONS") && "_quantity".equals(paramKey)) {
            ApiResponse<?> response = serviceHelper.callService(endpointName, Integer.parseInt(paramValue));
            context.set("lastResponse", response);
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put(paramKey, paramValue);
        ApiResponse<?> response = serviceHelper.callService(endpointName, params);
        context.set("lastResponse", response);
    }

    @When("send GET request to {string} with query parameters:")
    public void sendGetRequestWithQueryParams(String endpoint, Map<String, String> params) {
        String endpointName = serviceHelper.extractEndpointName(endpoint);
        Map<String, Object> objectParams = new HashMap<>(params);
        ApiResponse<?> response = serviceHelper.callService(endpointName, objectParams);
        context.set("lastResponse", response);
    }

    @When("send GET request to endpoint {word} with query parameters:")
    public void sendGetRequestToEndpointWithQueryParams(String endpointName, Map<String, String> params) {
        Map<String, Object> objectParams = new HashMap<>(params);
        ApiResponse<?> response = serviceHelper.callService(endpointName, objectParams);
        context.set("lastResponse", response);
    }

    @Then("verify status code is {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        serviceHelper.assertEquals(expectedStatusCode, serviceHelper.getLastResponseCode());
    }

    @Then("verify status code is {string}")
    public void verifyStatusCodeString(String statusCodeName) {
        StatusCode statusCode = StatusCode.valueOf(statusCodeName.toUpperCase());
        serviceHelper.assertEquals(statusCode.getCode(), serviceHelper.getLastResponseCode());
    }

    @Then("verify response is successful")
    public void verifyResponseIsSuccessful() {
        ApiResponse<?> response = serviceHelper.getLastResponse();
        Assert.assertTrue("API response was not successful", response.isSuccess());
        serviceHelper.assertEquals(StatusCode.SUCCESS.getCode(), serviceHelper.getLastResponseCode());
    }

    @Then("verify response contains field {string}")
    public void verifyFieldExists(String fieldPath) {
        ApiResponse<?> response = serviceHelper.getLastResponse();
        Object value = serviceHelper.getFieldValue(response, fieldPath);
        Assert.assertNotNull("Field '" + fieldPath + "' should not be null", value);
    }

    @Then("verify field {string} equals {string}")
    public void verifyFieldEqualsString(String fieldPath, String value) {
        ApiResponse<?> response = serviceHelper.getLastResponse();
        Object fieldValue = serviceHelper.getFieldValue(response, fieldPath);
        Assert.assertEquals("Field '" + fieldPath + "' mismatch", value, fieldValue);
    }

    @Then("verify list {string} has {int} items")
    public void verifyListSize(String arrayPath, int size) {
        ApiResponse<?> response = serviceHelper.getLastResponse();
        List<?> dataList = response.getData();
        Assert.assertEquals("List size mismatch for " + arrayPath, size, dataList.size());
    }

    @Then("verify list {string} has maximum {int} items")
    public void verifyListMaxSize(String arrayPath, int maxSize) {
        ApiResponse<?> response = serviceHelper.getLastResponse();
        List<?> dataList = response.getData();
        Assert.assertTrue("List size " + dataList.size() + " exceeds max " + maxSize, dataList.size() <= maxSize);
    }
}
