package com.practice.api.testcases.api00_options_request;

import com.practice.api.ApiResponse;
import com.practice.api.clients.ProductsApiClient;
import com.practice.base.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test 0: OPTIONS request smoke check")
class OptionsRequestTest extends BaseApiTest {

    private ProductsApiClient productsApiClient;

    @BeforeEach
    void setUp() {
        productsApiClient = new ProductsApiClient(apiRequestContext);
    }

    @Test
    @DisplayName("OPTIONS /productsList reports GET as an allowed method")
    void optionsProductsListReturnsAllowedMethods() {
        ApiResponse response = productsApiClient.optionsProductsList();

        assertThat(response.status()).isEqualTo(200);
        assertThat(response.header("allow")).contains("GET");
    }
}
