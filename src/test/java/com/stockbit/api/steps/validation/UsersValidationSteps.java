package com.stockbit.api.steps.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.context.TestContext;
import io.cucumber.java.en.And;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UsersValidationSteps {

    private final TestContext context;
    private final ObjectMapper objectMapper;

    public UsersValidationSteps() {
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

    @And("verify first user has field {string}")
    public void verifyFirstUserHasField(String field) {
        List<Map<String, Object>> users = getDataAsMapList();
        Map<String, Object> firstUser = users.get(0);
        assertTrue("First user missing field: " + field, firstUser.containsKey(field) && firstUser.get(field) != null);
    }

    @And("verify all users have valid email format")
    public void verifyAllUsersHaveValidEmailFormat() {
        List<Map<String, Object>> users = getDataAsMapList();
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        for (Map<String, Object> user : users) {
            String email = user.get("email") != null ? user.get("email").toString() : "";
            assertTrue("Invalid email format: " + email, emailPattern.matcher(email).matches());
        }
    }

    @And("verify all users have valid UUID format")
    public void verifyAllUsersHaveValidUUIDFormat() {
        List<Map<String, Object>> users = getDataAsMapList();
        Pattern uuidPattern = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", Pattern.CASE_INSENSITIVE);
        for (Map<String, Object> user : users) {
            String uuid = user.get("uuid") != null ? user.get("uuid").toString() : "";
            assertTrue("Invalid UUID format: " + uuid, uuidPattern.matcher(uuid).matches());
        }
    }

    @And("verify all users have valid IP address format")
    public void verifyAllUsersHaveValidIPFormat() {
        List<Map<String, Object>> users = getDataAsMapList();
        Pattern ipPattern = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");
        for (Map<String, Object> user : users) {
            String ip = user.get("ip") != null ? user.get("ip").toString() : "";
            assertTrue("Invalid IP format: " + ip, ipPattern.matcher(ip).matches());
        }
    }

    @And("verify all users have valid MAC address format")
    public void verifyAllUsersHaveValidMacAddressFormat() {
        List<Map<String, Object>> users = getDataAsMapList();
        Pattern macPattern = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
        for (Map<String, Object> user : users) {
            String mac = user.get("macAddress") != null ? user.get("macAddress").toString() : "";
            assertTrue("Invalid MAC address format: " + mac, macPattern.matcher(mac).matches());
        }
    }

    @And("verify all users have valid website URL format")
    public void verifyAllUsersHaveValidWebsiteURLFormat() {
        List<Map<String, Object>> users = getDataAsMapList();
        Pattern urlPattern = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        for (Map<String, Object> user : users) {
            String website = user.get("website") != null ? user.get("website").toString() : "";
            assertTrue("Invalid website URL format: " + website, urlPattern.matcher(website).matches());
        }
    }

    private void assertTrue(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
