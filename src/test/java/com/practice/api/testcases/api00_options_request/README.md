# API Test Case 00: OPTIONS Request Smoke Check

**Source:** Custom smoke test (not part of https://automationexercise.com/api_list)

**Endpoint:** `OPTIONS https://automationexercise.com/api/productsList`

## Description
Sanity check that the API framework (JDK `HttpClient` + Maven + JUnit) is wired up correctly before working through the 14 official API test cases. Confirmed manually with `curl -i -X OPTIONS`: the server responds `200` with an `Allow` header listing the methods it supports for this resource.

## Test Steps
1. Send an `OPTIONS` request to `/productsList`.
2. Verify the response status code is `200`.
3. Verify the `Allow` response header lists `GET` as a supported method.

## Expected Result
- Status code: 200
- `Allow` header contains `GET` (observed value at time of writing: `PUT, OPTIONS, GET, POST, DELETE`)

## Status
- [x] Implemented — see `OptionsRequestTest.java`
