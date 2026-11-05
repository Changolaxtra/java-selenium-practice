# API Test Case 07: POST Verify Login (Valid credentials)

**Source:** https://automationexercise.com/api_list (API 7)

**Endpoint:** `POST https://automationexercise.com/api/verifyLogin`

## Description
Verify login succeeds with valid, existing credentials.

## Preconditions
Requires an existing account (see ../api11_create_user_account/README.md) with a known email/password.

## Request Parameters
`email`, `password`

## Test Steps
1. Send a `POST` request to `/verifyLogin` with the parameters above.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200
- Response body `responseCode`: 200
- Response body `message`: "User exists!"

## Status
- [ ] Not implemented
