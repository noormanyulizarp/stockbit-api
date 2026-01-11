package com.stockbit.api.steps.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.context.TestContext;
import io.cucumber.java.en.And;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonValidationSteps {

    private final TestContext context;
    private final ObjectMapper objectMapper;

    public CommonValidationSteps() {
        this.context = TestContext.getInstance();
        this.objectMapper = new ObjectMapper();
    }

    private ApiResponse<?> getResponse() {
        return context.get("lastResponse", ApiResponse.class);
    }

    private List<Map<String, Object>> getDataAsMapList() {
        ApiResponse<?> response = getResponse();
        List<?> dataList = response.getData();
        return convertListToMapList(dataList);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> convertListToMapList(List<?> list) {
        return list.stream()
                .map(item -> (Map<String, Object>) objectMapper.convertValue(item, Map.class))
                .collect(Collectors.toList());
    }

    @And("verify first item has field {string}")
    public void verifyFirstItemHasField(String field) {
        List<Map<String, Object>> items = getDataAsMapList();
        Map<String, Object> firstItem = items.get(0);
        assertTrue("First item missing field: " + field, firstItem.containsKey(field) && firstItem.get(field) != null);
    }

    @And("verify all items have field {string}")
    public void verifyAllItemsHaveField(String field) {
        List<Map<String, Object>> items = getDataAsMapList();
        for (Map<String, Object> item : items) {
            assertTrue("Item missing field: " + field, item.containsKey(field));
        }
    }

    @And("verify all items have valid coordinates")
    public void verifyAllItemsHaveValidCoordinates() {
        List<Map<String, Object>> places = getDataAsMapList();
        for (Map<String, Object> place : places) {
            Object lat = place.get("latitude");
            Object lon = place.get("longitude");
            assertTrue("Invalid latitude", lat instanceof Number && ((Number) lat).doubleValue() >= -90 && ((Number) lat).doubleValue() <= 90);
            assertTrue("Invalid longitude", lon instanceof Number && ((Number) lon).doubleValue() >= -180 && ((Number) lon).doubleValue() <= 180);
        }
    }

    private void assertTrue(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
