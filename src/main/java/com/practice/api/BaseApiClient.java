package com.practice.api;

import com.google.gson.Gson;

/**
 * Thin wrapper around {@link ApiRequestContext}. Concrete clients (one per
 * resource/endpoint group) extend this and expose intention-revealing
 * methods instead of raw HTTP verbs.
 */
public abstract class BaseApiClient {

    protected final ApiRequestContext requestContext;
    protected final Gson gson = new Gson();

    protected BaseApiClient(ApiRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    protected ApiResponse get(String url) {
        return requestContext.get(url);
    }

    protected ApiResponse post(String url, Object body) {
        return requestContext.post(url, body);
    }

    protected ApiResponse put(String url, Object body) {
        return requestContext.put(url, body);
    }

    protected ApiResponse delete(String url) {
        return requestContext.delete(url);
    }

    protected ApiResponse options(String url) {
        return requestContext.options(url);
    }

    protected <T> T parse(ApiResponse response, Class<T> type) {
        return gson.fromJson(response.body(), type);
    }
}
