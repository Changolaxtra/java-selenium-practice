# API Test Case 04: PUT To All Brands List

**Source:** https://automationexercise.com/api_list (API 4)

**Endpoint:** `PUT https://automationexercise.com/api/brandsList`

## Description
Validate that this endpoint rejects the PUT method (GET-only endpoint).

## Request Parameters
None

## Test Steps
1. Send a `PUT` request to `/brandsList`.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200 (API always returns HTTP 200; the failure is signaled in the body)
- Response body `responseCode`: 405
- Response body `message`: "This request method is not supported"

## Status
- [ ] Not implemented
