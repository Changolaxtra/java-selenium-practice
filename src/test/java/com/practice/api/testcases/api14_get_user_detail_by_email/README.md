# API Test Case 14: GET User Detail by Email

**Source:** https://automationexercise.com/api_list (API 14)

**Endpoint:** `GET https://automationexercise.com/api/getUserDetailByEmail`

## Description
Retrieve a user's account detail by email address.

## Preconditions
Requires an existing account (see ../api11_create_user_account/README.md).

## Request Parameters
`email` (as a query parameter)

## Test Steps
1. Send a `GET` request to `/getUserDetailByEmail` with the parameters above.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200
- Response body `responseCode`: 200
- Response body `user` object contains the account's stored details.

## Status
- [ ] Not implemented
