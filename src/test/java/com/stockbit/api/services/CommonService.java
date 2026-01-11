package com.stockbit.api.services;

import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.config.Config;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class CommonService {
    static {
        RestAssured.baseURI = Config.getInstance().getBaseUrl();
    }

    private static RequestSpecification getRequestSpecification() {
        return given()
                .contentType("application/json")
                .accept("application/json")
                .log().ifValidationFails();
    }

    private static ApiResponse<Object> parseResponse(Response response) {
        ApiResponse<Object> apiResponse = response.as(new TypeRef<ApiResponse<Object>>() {});
        apiResponse.setHttpStatusCode(response.getStatusCode());
        return apiResponse;
    }

    /**
     * Generic method for raw endpoint calls (for negative tests like 404)
     */
    public static ApiResponse<Object> getRawEndpoint(String path) {
        Response response = getRequestSpecification()
                .when()
                .get(path);

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setHttpStatusCode(response.getStatusCode());

        // For error responses (non-200), just set the status code
        if (response.getStatusCode() != 200) {
            apiResponse.setStatus(response.getStatusLine());
            return apiResponse;
        }

        // For successful responses, parse as normal
        return response.as(new TypeRef<ApiResponse<Object>>() {});
    }
}
