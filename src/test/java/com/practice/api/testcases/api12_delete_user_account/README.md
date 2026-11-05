# API Test Case 12: DELETE User Account

**Source:** https://automationexercise.com/api_list (API 12)

**Endpoint:** `DELETE https://automationexercise.com/api/deleteAccount`

## Description
Delete an existing user account via the API.

## Preconditions
Requires an existing account (see ../api11_create_user_account/README.md).

## Request Parameters
`email`, `password`

## Test Steps
1. Send a `DELETE` request to `/deleteAccount` with the parameters above.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200
- Response body `responseCode`: 200
- Response body `message`: "Account deleted!"

## Status
- [ ] Not implemented
