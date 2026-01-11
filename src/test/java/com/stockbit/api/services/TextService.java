package com.stockbit.api.services;

import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.api.response.TextResponsePayload;
import com.stockbit.api.config.Config;
import com.stockbit.api.enums.Endpoint;
import com.stockbit.api.utils.RetryHelper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class TextService {

    static {
        RestAssured.baseURI = Config.getInstance().getBaseUrl();
    }

    private static RequestSpecification getRequestSpecification() {
        return given()
                .contentType("application/json")
                .accept("application/json")
                .log().ifValidationFails();
    }

    private static ApiResponse<TextResponsePayload> parseResponse(Response response) {
        ApiResponse<TextResponsePayload> apiResponse = response.as(new TypeRef<ApiResponse<TextResponsePayload>>() {});
        apiResponse.setHttpStatusCode(response.getStatusCode());
        return apiResponse;
    }

    public static ApiResponse<TextResponsePayload> getTexts() {
        Response response = RetryHelper.executeWithRetry(() ->
                getRequestSpecification()
                        .when()
                        .get(Endpoint.TEXTS.getFullPath())
        );
        return parseResponse(response);
    }

    public static ApiResponse<TextResponsePayload> getTexts(int quantity) {
        Response response = RetryHelper.executeWithRetry(() ->
                getRequestSpecification()
                        .queryParam("_quantity", quantity)
                        .when()
                        .get(Endpoint.TEXTS.getFullPath())
        );
        return parseResponse(response);
    }

    public static ApiResponse<TextResponsePayload> getTexts(Map<String, Object> params) {
        Response response = RetryHelper.executeWithRetry(() ->
                getRequestSpecification()
                        .queryParams(params)
                        .when()
                        .get(Endpoint.TEXTS.getFullPath())
        );
        return parseResponse(response);
    }
}
