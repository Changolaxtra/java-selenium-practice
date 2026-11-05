# API Test Case 02: POST To All Products List

**Source:** https://automationexercise.com/api_list (API 2)

**Endpoint:** `POST https://automationexercise.com/api/productsList`

## Description
Validate that this endpoint rejects the POST method (GET-only endpoint).

## Request Parameters
None

## Test Steps
1. Send a `POST` request to `/productsList`.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200 (API always returns HTTP 200; the failure is signaled in the body)
- Response body `responseCode`: 405
- Response body `message`: "This request method is not supported"

## Status
- [ ] Not implemented
