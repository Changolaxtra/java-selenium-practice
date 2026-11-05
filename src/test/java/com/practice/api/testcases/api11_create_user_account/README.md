# API Test Case 11: POST Create User Account

**Source:** https://automationexercise.com/api_list (API 11)

**Endpoint:** `POST https://automationexercise.com/api/createAccount`

## Description
Create a new user account via the API. This account is the fixture other API tests (7, 12, 13, 14) reuse.

## Request Parameters
`name`, `email`, `password`, `title`, `birth_date`, `birth_month`, `birth_year`, `firstname`, `lastname`, `company`, `address1`, `address2`, `country`, `zipcode`, `state`, `city`, `mobile_number`

## Test Steps
1. Send a `POST` request to `/createAccount` with the parameters above.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200
- Response body `responseCode`: 201
- Response body `message`: "User created!"

## Status
- [ ] Not implemented
