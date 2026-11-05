# Test Case 00: Visit Home Page

**Source:** Custom smoke test (not part of https://automationexercise.com/test_cases)

## Description
Sanity check that the UI framework (Selenium + Maven + JUnit) is wired up correctly against the real site before working through the 26 official test cases.

## Test Steps
1. Launch browser
2. Navigate to `https://automationexercise.com`
3. Verify the home page loaded successfully: page title is "Automation Exercise", the "Home" nav link is visible, and the site slogan is visible

## Status
- [x] Implemented — see `VisitHomePageTest.java`
