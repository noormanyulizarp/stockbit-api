package com.stockbit.api.services;

import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.api.response.UserResponsePayload;
import com.stockbit.api.config.Config;
import com.stockbit.api.enums.Endpoint;
import com.stockbit.api.utils.RetryHelper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserService {

    static {
        RestAssured.baseURI = Config.getInstance().getBaseUrl();
    }

    private static RequestSpecification getRequestSpecification() {
        return given()
                .contentType("application/json")
                .accept("application/json")
                .log().ifValidationFails();
    }

    private static ApiResponse<UserResponsePayload> parseResponse(Response response) {
        ApiResponse<UserResponsePayload> apiResponse = response.as(new TypeRef<ApiResponse<UserResponsePayload>>() {});
        apiResponse.setHttpStatusCode(response.getStatusCode());
        return apiResponse;
    }

    public static ApiResponse<UserResponsePayload> getUsers() {
        Response response = RetryHelper.executeWithRetry(() ->
                getRequestSpecification()
                        .when()
                        .get(Endpoint.USERS.getFullPath())
        );
        return parseResponse(response);
    }

    public static ApiResponse<UserResponsePayload> getUsers(int quantity) {
        Response response = RetryHelper.executeWithRetry(() ->
                getRequestSpecification()
                        .queryParam("_quantity", quantity)
                        .when()
                        .get(Endpoint.USERS.getFullPath())
        );
        return parseResponse(response);
    }

    public static ApiResponse<UserResponsePayload> getUsers(String locale, int quantity) {
        Response response = RetryHelper.executeWithRetry(() ->
                getRequestSpecification()
                        .queryParam("_quantity", quantity)
                        .queryParam("_locale", locale)
                        .when()
                        .get(Endpoint.USERS.getFullPath())
        );
        return parseResponse(response);
    }

    public static ApiResponse<UserResponsePayload> getUsers(Map<String, Object> params) {
        Response response = RetryHelper.executeWithRetry(() ->
                getRequestSpecification()
                        .queryParams(params)
                        .when()
                        .get(Endpoint.USERS.getFullPath())
        );
        return parseResponse(response);
    }

    public static ApiResponse<UserResponsePayload> getUsers(int quantity, String seed) {
        Response response = RetryHelper.executeWithRetry(() ->
                getRequestSpecification()
                        .queryParam("_quantity", quantity)
                        .queryParam("_seed", seed)
                        .when()
                        .get(Endpoint.USERS.getFullPath())
        );
        return parseResponse(response);
    }
}
