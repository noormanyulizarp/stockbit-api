package com.stockbit.api.utils;

import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.context.TestContext;
import com.stockbit.api.enums.Endpoint;
import com.stockbit.api.enums.Locale;
import com.stockbit.api.services.AddressService;
import com.stockbit.api.services.BookService;
import com.stockbit.api.services.CompanyService;
import com.stockbit.api.services.CreditCardService;
import com.stockbit.api.services.ImageService;
import com.stockbit.api.services.PersonService;
import com.stockbit.api.services.PlaceService;
import com.stockbit.api.services.ProductService;
import com.stockbit.api.services.TextService;
import com.stockbit.api.services.UserService;

import java.util.HashMap;
import java.util.Map;

public class ServiceHelper {

    private final TestContext context;

    public ServiceHelper() {
        this.context = TestContext.getInstance();
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
        if (path.equalsIgnoreCase("creditcards")) return "CREDIT_CARDS";
        return path.toUpperCase();
    }

    public ApiResponse<?> callService(String endpointName, int quantity) {
        Endpoint endpoint = Endpoint.valueOf(endpointName.toUpperCase());

        switch (endpoint) {
            case USERS:
                return quantity > 0 ? UserService.getUsers(quantity) : UserService.getUsers();
            case PRODUCTS:
                return quantity > 0 ? ProductService.getProducts(quantity) : ProductService.getProducts();
            case BOOKS:
                return quantity > 0 ? BookService.getBooks(quantity) : BookService.getBooks();
            case ADDRESSES:
                return quantity > 0 ? AddressService.getAddresses(quantity) : AddressService.getAddresses();
            case COMPANIES:
                return quantity > 0 ? CompanyService.getCompanies(quantity) : CompanyService.getCompanies();
            case CREDIT_CARDS:
                return quantity > 0 ? CreditCardService.getCreditCards(quantity) : CreditCardService.getCreditCards();
            case IMAGES:
                return quantity > 0 ? ImageService.getImages(quantity) : ImageService.getImages();
            case TEXTS:
                return quantity > 0 ? TextService.getTexts(quantity) : TextService.getTexts();
            case PLACES:
                return quantity > 0 ? PlaceService.getPlaces(quantity) : PlaceService.getPlaces();
            case PERSONS:
                return PersonService.getPersons(quantity);
            default:
                throw new IllegalArgumentException("Unknown endpoint: " + endpoint);
        }
    }

    public ApiResponse<?> callServiceWithParam(String endpointName, String paramKey, String paramValue) {
        Endpoint endpoint = Endpoint.valueOf(endpointName.toUpperCase());

        switch (endpoint) {
            case USERS:
                if (paramKey.equals("_quantity")) {
                    return UserService.getUsers(Integer.parseInt(paramValue));
                }
                if (paramKey.equals("_locale")) {
                    String locale = getLocaleCodeOrDefault(paramValue);
                    return UserService.getUsers(locale, 10);
                }
                if (paramKey.equals("_seed")) {
                    return UserService.getUsers(10, paramValue);
                }
                // Default - use params map
                Map<String, Object> userParams = new HashMap<>();
                userParams.put(paramKey, paramValue);
                return UserService.getUsers(userParams);
            case PERSONS:
                if (paramKey.equals("_quantity")) {
                    return PersonService.getPersons(Integer.parseInt(paramValue));
                }
                return PersonService.getPersons(paramValue, 10);
            default:
                Map<String, Object> params = new HashMap<>();
                params.put(paramKey, paramValue);
                return callServiceWithParams(endpointName, params);
        }
    }

    public ApiResponse<?> callServiceWithParams(String endpointName, Map<String, Object> params) {
        Endpoint endpoint = Endpoint.valueOf(endpointName.toUpperCase());

        switch (endpoint) {
            case USERS:
                return UserService.getUsers(params);
            case PRODUCTS:
                return ProductService.getProducts(params);
            case BOOKS:
                return BookService.getBooks(params);
            case ADDRESSES:
                return AddressService.getAddresses(params);
            case COMPANIES:
                return CompanyService.getCompanies(params);
            case CREDIT_CARDS:
                return CreditCardService.getCreditCards(params);
            case IMAGES:
                return ImageService.getImages(params);
            case TEXTS:
                return TextService.getTexts(params);
            case PLACES:
                return PlaceService.getPlaces(params);
            case PERSONS:
                return PersonService.getPersons(params);
            default:
                throw new IllegalArgumentException("Unknown endpoint: " + endpoint);
        }
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
