# API Test Case 08: POST Verify Login (Missing email parameter)

**Source:** https://automationexercise.com/api_list (API 8)

**Endpoint:** `POST https://automationexercise.com/api/verifyLogin`

## Description
Validate the required-parameter check on the login endpoint.

## Request Parameters
`password` only (email intentionally omitted)

## Test Steps
1. Send a `POST` request to `/verifyLogin` with the parameters above.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200 (API always returns HTTP 200; the failure is signaled in the body)
- Response body `responseCode`: 400
- Response body `message`: "Bad request, email or password parameter is missing"

## Status
- [ ] Not implemented
