package com.practice.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Minimal HTTP client scoped to a base URL, built on the JDK's
 * {@link HttpClient} (Selenium has no API-testing support of its own).
 * Relative paths are resolved against the base URL per {@link URI#resolve},
 * so a path starting with {@code /} replaces the base path entirely.
 */
public class ApiRequestContext {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();
    private final URI baseUri;

    public ApiRequestContext(String baseUrl) {
        this.baseUri = URI.create(baseUrl);
    }

    public ApiResponse get(String path) {
        return send(request(path).GET().build());
    }

    public ApiResponse post(String path, Object body) {
        return send(request(path).POST(jsonBody(body)).build());
    }

    public ApiResponse put(String path, Object body) {
        return send(request(path).PUT(jsonBody(body)).build());
    }

    public ApiResponse delete(String path) {
        return send(request(path).DELETE().build());
    }

    public ApiResponse options(String path) {
        return send(request(path).method("OPTIONS", HttpRequest.BodyPublishers.noBody()).build());
    }

    private HttpRequest.Builder request(String path) {
        return HttpRequest.newBuilder(baseUri.resolve(path));
    }

    private HttpRequest.BodyPublisher jsonBody(Object body) {
        return HttpRequest.BodyPublishers.ofString(gson.toJson(body));
    }

    private ApiResponse send(HttpRequest request) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new ApiResponse(response.statusCode(), response.uri().toString(), response.headers(), response.body());
        } catch (IOException e) {
            throw new IllegalStateException("HTTP request failed: " + request.method() + " " + request.uri(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("HTTP request interrupted: " + request.method() + " " + request.uri(), e);
        }
    }
}
