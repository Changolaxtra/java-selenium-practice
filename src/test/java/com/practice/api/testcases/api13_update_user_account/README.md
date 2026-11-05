# API Test Case 13: PUT Update User Account

**Source:** https://automationexercise.com/api_list (API 13)

**Endpoint:** `PUT https://automationexercise.com/api/updateAccount`

## Description
Update the details of an existing user account via the API.

## Preconditions
Requires an existing account (see ../api11_create_user_account/README.md).

## Request Parameters
`name`, `email`, `password`, `title`, `birth_date`, `birth_month`, `birth_year`, `firstname`, `lastname`, `company`, `address1`, `address2`, `country`, `zipcode`, `state`, `city`, `mobile_number`

## Test Steps
1. Send a `PUT` request to `/updateAccount` with the parameters above.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200
- Response body `responseCode`: 200
- Response body `message`: "User updated!"

## Status
- [ ] Not implemented
