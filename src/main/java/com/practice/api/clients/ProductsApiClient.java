package com.practice.api.clients;

import com.practice.api.ApiRequestContext;
import com.practice.api.ApiResponse;
import com.practice.api.BaseApiClient;

public class ProductsApiClient extends BaseApiClient {

    private static final String PRODUCTS_LIST_PATH = "productsList";

    public ProductsApiClient(ApiRequestContext requestContext) {
        super(requestContext);
    }

    public ApiResponse optionsProductsList() {
        return options(PRODUCTS_LIST_PATH);
    }
}
