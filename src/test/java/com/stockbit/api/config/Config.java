package com.stockbit.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static Config instance;
    private Properties properties;

    private Config() {
        properties = new Properties();
        loadProperties();
        setDefaultProperties();
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public String getApiEndpoint() {
        return properties.getProperty("api.endpoint");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getFullApiUrl() {
        return getBaseUrl() + getApiEndpoint();
    }

    public int getRequestTimeout() {
        try {
            return Integer.parseInt(properties.getProperty("request.timeout"));
        } catch (NumberFormatException e) {
            return 10000;
        }
    }

    private void loadProperties() {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                setDefaultProperties();
                return;
            }
            properties.load(input);
        } catch (IOException e) {
            setDefaultProperties();
        }
    }

    private void setDefaultProperties() {
        if (!properties.containsKey("base.url")) {
            properties.setProperty("base.url", "https://fakerapi.it");
        }
        if (!properties.containsKey("api.endpoint")) {
            properties.setProperty("api.endpoint", "/api/v2");
        }
        if (!properties.containsKey("request.timeout")) {
            properties.setProperty("request.timeout", "10000");
        }
    }
}
