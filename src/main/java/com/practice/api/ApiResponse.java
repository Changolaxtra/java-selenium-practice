package com.practice.api;

import java.net.http.HttpHeaders;

/**
 * Immutable snapshot of an HTTP response, so tests and API clients never
 * touch {@code java.net.http} types directly.
 */
public record ApiResponse(int status, String url, HttpHeaders headers, String body) {

    /** Case-insensitive header lookup; returns {@code null} when absent. */
    public String header(String name) {
        return headers.firstValue(name).orElse(null);
    }
}
