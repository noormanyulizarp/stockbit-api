package com.stockbit.api.context;

import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private static TestContext instance;
    private Response apiResponse;
    private String baseUrl;
    private Map<String, Object> data;

    private TestContext() {
        data = new HashMap<>();
    }

    public static TestContext getInstance() {
        if (instance == null) {
            instance = new TestContext();
        }
        return instance;
    }

    public Response getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(Response apiResponse) {
        this.apiResponse = apiResponse;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public <T> T get(String key, Class<T> type) {
        Object value = data.get(key);
        if (value == null) {
            return null;
        }
        return type.cast(value);
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }

    public static void reset() {
        instance = new TestContext();
    }
}
