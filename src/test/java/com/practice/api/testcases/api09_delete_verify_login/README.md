# API Test Case 09: DELETE Verify Login

**Source:** https://automationexercise.com/api_list (API 9)

**Endpoint:** `DELETE https://automationexercise.com/api/verifyLogin`

## Description
Validate that this endpoint rejects the DELETE method (POST-only endpoint).

## Request Parameters
None

## Test Steps
1. Send a `DELETE` request to `/verifyLogin`.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200 (API always returns HTTP 200; the failure is signaled in the body)
- Response body `responseCode`: 405
- Response body `message`: "This request method is not supported"

## Status
- [ ] Not implemented
