package com.stockbit.api.utils;

import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.context.TestContext;
import com.stockbit.api.enums.Endpoint;
import com.stockbit.api.enums.Locale;
import com.stockbit.api.services.*;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ServiceHelper {

    private final TestContext context;

    public ServiceHelper() {
        this.context = TestContext.getInstance();
    }

    public ApiResponse<?> callService(String endpointName, Object... args) {
        Endpoint endpoint = Endpoint.valueOf(endpointName.toUpperCase());
        
        switch (endpoint) {
            case USERS:
                return handleUsersCall(args);
            case PRODUCTS:
                return handleSimpleCall(ProductService::getProducts, ProductService::getProducts, ProductService::getProducts, "PRODUCTS", args);
            case BOOKS:
                return handleSimpleCall(BookService::getBooks, BookService::getBooks, BookService::getBooks, "BOOKS", args);
            case ADDRESSES:
                return handleSimpleCall(AddressService::getAddresses, AddressService::getAddresses, AddressService::getAddresses, "ADDRESSES", args);
            case COMPANIES:
                return handleSimpleCall(CompanyService::getCompanies, CompanyService::getCompanies, CompanyService::getCompanies, "COMPANIES", args);
            case CREDIT_CARDS:
                return handleSimpleCall(CreditCardService::getCreditCards, CreditCardService::getCreditCards, CreditCardService::getCreditCards, "CREDIT_CARDS", args);
            case IMAGES:
                return handleSimpleCall(ImageService::getImages, ImageService::getImages, ImageService::getImages, "IMAGES", args);
            case TEXTS:
                return handleSimpleCall(TextService::getTexts, TextService::getTexts, TextService::getTexts, "TEXTS", args);
            case PLACES:
                return handleSimpleCall(PlaceService::getPlaces, PlaceService::getPlaces, PlaceService::getPlaces, "PLACES", args);
            case PERSONS:
                return handlePersonsCall(args);
            default:
                throw new IllegalArgumentException("Unknown endpoint: " + endpoint);
        }
    }

    private ApiResponse<?> handleSimpleCall(
            Supplier<ApiResponse<?>> noArgs,
            Function<Integer, ApiResponse<?>> withQuantity,
            Function<Map<String, Object>, ApiResponse<?>> withParams,
            String serviceName,
            Object[] args) {
        
        if (args.length == 0) {
            return noArgs.get();
        }
        if (args.length == 1 && args[0] instanceof Integer) {
            return withQuantity.apply((Integer) args[0]);
        }
        if (args.length == 1 && args[0] instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> params = (Map<String, Object>) args[0];
            return withParams.apply(params);
        }
        throw new IllegalArgumentException("Invalid arguments for " + serviceName + " service");
    }

    private ApiResponse<?> handleUsersCall(Object[] args) {
        if (args.length == 0) {
            return UserService.getUsers();
        }
        if (args.length == 1 && args[0] instanceof Integer) {
            return UserService.getUsers((Integer) args[0]);
        }
        if (args.length == 2) {
            if (args[0] instanceof String && args[1] instanceof Integer) {
                return UserService.getUsers((String) args[0], (Integer) args[1]);
            }
            if (args[0] instanceof Integer && args[1] instanceof String) {
                return UserService.getUsers((Integer) args[0], (String) args[1]);
            }
        }
        if (args.length == 1 && args[0] instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> params = (Map<String, Object>) args[0];
            return UserService.getUsers(params);
        }
        throw new IllegalArgumentException("Invalid arguments for USERS service");
    }

    private ApiResponse<?> handlePersonsCall(Object[] args) {
        if (args.length == 1 && args[0] instanceof Integer) {
            return PersonService.getPersons((Integer) args[0]);
        }
        if (args.length == 2 && args[0] instanceof String && args[1] instanceof Integer) {
            return PersonService.getPersons((String) args[0], (Integer) args[1]);
        }
        if (args.length == 1 && args[0] instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> params = (Map<String, Object>) args[0];
            return PersonService.getPersons(params);
        }
        return PersonService.getPersons(10);
    }

    public String extractEndpointName(String endpoint) {
        if (endpoint.contains("/")) {
            String[] parts = endpoint.split("/");
            String lastPart = parts[parts.length - 1];
            return convertToEndpointName(lastPart);
        }
        return endpoint.toUpperCase();
    }

    public String convertToEndpointName(String path) {
        if ("creditcards".equalsIgnoreCase(path)) {
            return "CREDIT_CARDS";
        }
        return path.toUpperCase();
    }

    public ApiResponse<?> getLastResponse() {
        return context.get("lastResponse", ApiResponse.class);
    }

    public Integer getLastResponseCode() {
        ApiResponse<?> response = getLastResponse();
        Integer code = response.getHttpStatusCode();
        if (code == null) {
            throw new AssertionError("HTTP status code is null! Response status: " + response.getStatus() + ", code: " + response.getCode());
        }
        return code;
    }

    public Object getFieldValue(ApiResponse<?> response, String fieldPath) {
        if (fieldPath.equals("status")) return response.getStatus();
        if (fieldPath.equals("data")) return response.getData();
        if (fieldPath.equals("total")) return response.getTotal();
        if (fieldPath.equals("locale")) return response.getLocale();
        if (fieldPath.equals("seed")) return response.getSeed();
        return null;
    }

    public String getLocaleCodeOrDefault(String value) {
        try {
            Locale locale = Locale.valueOf(value);
            return locale.getCode();
        } catch (IllegalArgumentException e) {
            return value;
        }
    }

    public void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }
}
