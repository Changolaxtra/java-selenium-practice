# API Test Case 06: POST Search Without Parameter

**Source:** https://automationexercise.com/api_list (API 6)

**Endpoint:** `POST https://automationexercise.com/api/searchProduct`

## Description
Validate the required-parameter check on the search endpoint.

## Request Parameters
None (intentionally omitted)

## Test Steps
1. Send a `POST` request to `/searchProduct` with the parameters above.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200 (API always returns HTTP 200; the failure is signaled in the body)
- Response body `responseCode`: 400
- Response body `message`: "Bad request, search_product parameter is missing"

## Status
- [ ] Not implemented
