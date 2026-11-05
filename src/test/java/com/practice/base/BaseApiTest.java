package com.practice.base;

import com.practice.api.ApiRequestContext;
import com.practice.config.ConfigReader;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base class for API tests. A single {@link ApiRequestContext}, scoped to
 * {@code api.base.url}, is shared across all tests in a class since it is
 * cheap to reuse and has no browser state to leak between tests.
 */
public abstract class BaseApiTest {

    protected static ApiRequestContext apiRequestContext;

    @BeforeAll
    static void createApiContext() {
        apiRequestContext = new ApiRequestContext(ConfigReader.apiBaseUrl());
    }
}
