# API Test Case 01: Get All Products List

**Source:** https://automationexercise.com/api_list (API 1)

**Endpoint:** `GET https://automationexercise.com/api/productsList`

## Description
Retrieve the complete product catalog.

## Request Parameters
None

## Test Steps
1. Send a `GET` request to `/productsList`.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200
- Response body contains a `products` array with the full product catalog.

## Status
- [ ] Not implemented
