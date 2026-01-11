package com.stockbit.api.services;

import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.api.response.PlaceResponsePayload;
import com.stockbit.api.config.Config;
import com.stockbit.api.enums.Endpoint;
import com.stockbit.api.utils.RetryHelper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class PlaceService {

    static {
        RestAssured.baseURI = Config.getInstance().getBaseUrl();
    }

    private static RequestSpecification getRequestSpecification() {
        return given()
                .contentType("application/json")
                .accept("application/json")
                .log().ifValidationFails();
    }

    private static ApiResponse<PlaceResponsePayload> parseResponse(Response response) {
        ApiResponse<PlaceResponsePayload> apiResponse = response.as(new TypeRef<ApiResponse<PlaceResponsePayload>>() {});
        apiResponse.setHttpStatusCode(response.getStatusCode());
        return apiResponse;
    }

    public static ApiResponse<PlaceResponsePayload> getPlaces() {
        Response response = RetryHelper.executeWithRetry(() ->
                getRequestSpecification()
                        .when()
                        .get(Endpoint.PLACES.getFullPath())
        );
        return parseResponse(response);
    }

    public static ApiResponse<PlaceResponsePayload> getPlaces(int quantity) {
        Response response = RetryHelper.executeWithRetry(() ->
                getRequestSpecification()
                        .queryParam("_quantity", quantity)
                        .when()
                        .get(Endpoint.PLACES.getFullPath())
        );
        return parseResponse(response);
    }

    public static ApiResponse<PlaceResponsePayload> getPlaces(Map<String, Object> params) {
        Response response = RetryHelper.executeWithRetry(() ->
                getRequestSpecification()
                        .queryParams(params)
                        .when()
                        .get(Endpoint.PLACES.getFullPath())
        );
        return parseResponse(response);
    }
}
