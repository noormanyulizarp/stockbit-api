package com.stockbit.api.enums;

public enum Endpoint {

    USERS("/users", "Users API"),
    PRODUCTS("/products", "Products API"),
    PERSONS("/persons", "Persons API"),
    COMPANIES("/companies", "Companies API"),
    BOOKS("/books", "Books API"),
    CREDIT_CARDS("/creditCards", "Credit Cards API"),
    IMAGES("/images", "Images API"),
    TEXTS("/texts", "Texts API"),
    PLACES("/places", "Places API"),
    ADDRESSES("/addresses", "Addresses API");

    private final String path;
    private final String name;

    Endpoint(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return path;
    }

    public String getFullPath() {
        return "/api/v2" + path;
    }
}
