package com.stockbit.api.steps.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.context.TestContext;
import io.cucumber.java.en.And;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductsValidationSteps {

    private final TestContext context;
    private final ObjectMapper objectMapper;

    public ProductsValidationSteps() {
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

    @And("verify first product has field {string}")
    public void verifyFirstProductHasField(String field) {
        List<Map<String, Object>> products = getDataAsMapList();
        Map<String, Object> firstProduct = products.get(0);
        assertTrue("First product missing field: " + field, firstProduct.containsKey(field) && firstProduct.get(field) != null);
    }

    @And("verify first product has images")
    public void verifyFirstProductHasImages() {
        List<Map<String, Object>> products = getDataAsMapList();
        Map<String, Object> firstProduct = products.get(0);
        List<?> images = (List<?>) firstProduct.get("images");
        assertTrue("Product images list is empty", images != null && !images.isEmpty());
    }

    @And("verify all products have taxes {int}")
    public void verifyAllProductsHaveTaxes(int expectedTaxes) {
        List<Map<String, Object>> products = getDataAsMapList();
        for (Map<String, Object> product : products) {
            Object taxes = product.get("taxes");
            int taxValue = taxes instanceof Number ? ((Number) taxes).intValue() : 0;
            assertEquals("Tax value mismatch", expectedTaxes, taxValue);
        }
    }

    @And("verify all products have field {string}")
    public void verifyAllProductsHaveField(String field) {
        List<Map<String, Object>> products = getDataAsMapList();
        for (Map<String, Object> product : products) {
            assertTrue("Product missing field: " + field, product.containsKey(field));
        }
    }

    @And("verify all products have non-empty tags")
    public void verifyAllProductsHaveNonEmptyTags() {
        List<Map<String, Object>> products = getDataAsMapList();
        for (Map<String, Object> product : products) {
            List<?> tags = (List<?>) product.get("tags");
            assertTrue("Product tags list is null or empty", tags != null && !tags.isEmpty());
        }
    }

    @And("verify all products have non-empty categories")
    public void verifyAllProductsHaveNonEmptyCategories() {
        List<Map<String, Object>> products = getDataAsMapList();
        for (Map<String, Object> product : products) {
            List<?> categories = (List<?>) product.get("categories");
            assertTrue("Product categories list is null or empty", categories != null && !categories.isEmpty());
        }
    }

    private void assertTrue(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private void assertEquals(String message, int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError(message + " - expected: " + expected + ", actual: " + actual);
        }
    }
}
