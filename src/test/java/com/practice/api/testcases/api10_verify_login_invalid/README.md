# API Test Case 10: POST Verify Login (Invalid credentials)

**Source:** https://automationexercise.com/api_list (API 10)

**Endpoint:** `POST https://automationexercise.com/api/verifyLogin`

## Description
Verify login fails for an email/password combination that does not exist.

## Request Parameters
`email`, `password` (values that don't match any account)

## Test Steps
1. Send a `POST` request to `/verifyLogin` with the parameters above.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200 (API always returns HTTP 200; the failure is signaled in the body)
- Response body `responseCode`: 404
- Response body `message`: "User not found!"

## Status
- [ ] Not implemented
