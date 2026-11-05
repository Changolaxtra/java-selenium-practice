# API Test Case 03: Get All Brands List

**Source:** https://automationexercise.com/api_list (API 3)

**Endpoint:** `GET https://automationexercise.com/api/brandsList`

## Description
Retrieve the complete brand catalog.

## Request Parameters
None

## Test Steps
1. Send a `GET` request to `/brandsList`.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200
- Response body contains a `brands` array with the full brand catalog.

## Status
- [ ] Not implemented
