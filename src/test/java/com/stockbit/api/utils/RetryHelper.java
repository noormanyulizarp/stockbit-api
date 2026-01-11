package com.stockbit.api.utils;

import dev.failsafe.RetryPolicy;
import dev.failsafe.Failsafe;
import io.restassured.response.Response;

import java.time.Duration;
import java.util.function.Supplier;

public class RetryHelper {

    private static final RetryPolicy<Response> retryPolicy = RetryPolicy.<Response>builder()
            .handleResultIf(response -> response.getStatusCode() == 429)
            .withDelay(Duration.ofSeconds(2))
            .withMaxRetries(6)
            .onSuccess(e -> {
                try {
                    Thread.sleep(120);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            })
            .build();

    public static Response executeWithRetry(Supplier<Response> operation) {
        return Failsafe.with(retryPolicy).get(operation::get);
    }
}
