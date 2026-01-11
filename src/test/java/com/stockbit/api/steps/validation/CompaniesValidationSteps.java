package com.stockbit.api.steps.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.context.TestContext;
import io.cucumber.java.en.And;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CompaniesValidationSteps {

    private final TestContext context;
    private final ObjectMapper objectMapper;

    public CompaniesValidationSteps() {
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

    @And("verify first company has field {string}")
    public void verifyFirstCompanyHasField(String field) {
        List<Map<String, Object>> companies = getDataAsMapList();
        Map<String, Object> firstCompany = companies.get(0);
        assertTrue("First company missing field: " + field, firstCompany.containsKey(field) && firstCompany.get(field) != null);
    }

    @And("verify all companies have valid email format")
    public void verifyAllCompaniesHaveValidEmailFormat() {
        List<Map<String, Object>> companies = getDataAsMapList();
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        for (Map<String, Object> company : companies) {
            String email = company.get("email") != null ? company.get("email").toString() : "";
            assertTrue("Invalid email format: " + email, emailPattern.matcher(email).matches());
        }
    }

    @And("verify all companies have valid website URL format")
    public void verifyAllCompaniesHaveValidWebsiteURLFormat() {
        List<Map<String, Object>> companies = getDataAsMapList();
        Pattern urlPattern = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        for (Map<String, Object> company : companies) {
            String website = company.get("website") != null ? company.get("website").toString() : "";
            assertTrue("Invalid website URL format: " + website, urlPattern.matcher(website).matches());
        }
    }

    @And("verify all companies have non-empty addresses")
    public void verifyAllCompaniesHaveNonEmptyAddresses() {
        List<Map<String, Object>> companies = getDataAsMapList();
        for (Map<String, Object> company : companies) {
            List<?> addresses = (List<?>) company.get("addresses");
            assertTrue("Company addresses list is null or empty", addresses != null && !addresses.isEmpty());
        }
    }

    @And("verify all companies have non-empty contact")
    public void verifyAllCompaniesHaveNonEmptyContact() {
        List<Map<String, Object>> companies = getDataAsMapList();
        for (Map<String, Object> company : companies) {
            Map<?, ?> contact = (Map<?, ?>) company.get("contact");
            assertTrue("Company contact is null or empty", contact != null && !contact.isEmpty());
        }
    }

    @And("verify all companies have field {string}")
    public void verifyAllCompaniesHaveField(String field) {
        List<Map<String, Object>> companies = getDataAsMapList();
        for (Map<String, Object> company : companies) {
            assertTrue("Company missing field: " + field, company.containsKey(field));
        }
    }

    private void assertTrue(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
