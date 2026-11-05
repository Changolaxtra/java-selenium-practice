# API Test Case 05: POST To Search Product

**Source:** https://automationexercise.com/api_list (API 5)

**Endpoint:** `POST https://automationexercise.com/api/searchProduct`

## Description
Search the product catalog by keyword.

## Request Parameters
`search_product` (examples: `top`, `tshirt`, `jean`)

## Test Steps
1. Send a `POST` request to `/searchProduct` with the parameters above.
2. Verify the response status code.
3. Verify the response body (`responseCode` / `message` / data) matches the expected result below.

## Expected Result
- Status code: 200
- Response body `responseCode`: 200
- Response body contains a `products` array with items matching the search term.

## Status
- [ ] Not implemented
