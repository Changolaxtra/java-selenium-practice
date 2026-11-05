# CLAUDE.md

This file guides Claude Code (and anyone else touching this repo) on how to work in this project.

## Project overview

A practice project for testing **[automationexercise.com](https://automationexercise.com/)** (UI) and its API with **Java 21**, **Maven**, and **Selenium WebDriver** (`org.seleniumhq.selenium`). Selenium drives the browser (UI tests); API tests use the JDK's `java.net.http.HttpClient` through `ApiRequestContext`. This project is the Selenium sibling of `java-playwright-practice` (same structure and test cases).

Test targets, configured in `config.properties`:
- UI: https://automationexercise.com — test cases enumerated at https://automationexercise.com/test_cases
- API: https://automationexercise.com/api — test cases enumerated at https://automationexercise.com/api_list

This is a learning project: test cases are documented one-by-one before they're implemented (see "Test case workflow" below). Don't implement a test case's logic unless asked — the point is for the project owner to practice writing it.

## Commands

```bash
mvn test                                   # run all tests
mvn test -Dtest=Tc01RegisterUserTest       # run a single test class, once it exists
mvn test -Dheadless=true                   # override config.properties at runtime
mvn compile                                 # compile main sources only

# no driver install step: Selenium Manager fetches chromedriver/geckodriver automatically
```

## Project structure

```
src/main/java/com/practice/
  config/     ConfigReader - reads config.properties, overridable via -Dkey=value
  ui/pages/   Page objects
  api/        ApiRequestContext + ApiResponse (JDK HttpClient), BaseApiClient + API clients (api/clients) + response/request models (api/model)

src/test/java/com/practice/
  base/               BaseUiTest / BaseApiTest (JUnit 5 lifecycle)
  ui/testcases/        tcNN_<slug>/README.md per UI test case (test_cases page)
  api/testcases/       apiNN_<slug>/README.md per API test case (api_list page)
```

Keep this split: **main** holds reusable framework code (page objects, API clients, config), **test** holds only test classes and test-specific fixtures. This lets page objects/API clients be reused from multiple test classes without living in test scope.

## Test case workflow

Each `testcases/tcNN_<slug>/` (UI) or `testcases/apiNN_<slug>/` (API) folder documents exactly one test case from the site (`README.md`: description, preconditions, steps, expected result, and a `- [ ] Not implemented` status line).

When implementing a test case:
1. Read its `README.md` for the steps and expected result — don't re-derive them from the live site.
2. Add the test class in the **same folder** as the README (e.g. `ui/testcases/tc01_register_user/RegisterUserTest.java`, `api/testcases/api11_create_user_account/CreateUserAccountTest.java`), extending `BaseUiTest` or `BaseApiTest`.
3. Reuse or add page objects (`ui/pages`) / API clients (`api/clients`) under `src/main/java` rather than duplicating locators or endpoint calls across test cases — several UI test cases share flows (e.g. signup, add-to-cart, checkout) and several API test cases share the same account fixture (test case 11, Create User Account).
4. Flip the README's status line to `- [x] Implemented` once the test class is done and passing.

Don't write the test class yourself unless explicitly asked — flag which page object/API client would be needed and let the project owner implement the test body as practice.

## Java conventions (Java 21)

- Prefer `record` for immutable data (API payloads, DTOs) under `api/model`.
- Use `var` for obvious local types; use explicit types when it aids readability (e.g. Selenium's `By`, `WebElement`, `ApiResponse`).
- Use switch expressions (`->` form) over classic `switch` statements, e.g. `BaseUiTest.createDriver()`.
- Favor composition over inheritance beyond the single base-class level already in use (`BasePage`, `BaseApiClient`, `BaseUiTest`, `BaseApiTest`). Don't build deep class hierarchies.
- No Lombok — the codebase is small enough that plain constructors/accessors (or records) are clearer than annotation-generated code.
- Package by feature/layer (`ui.pages`, `api.clients`, `api.model`), not by type-only buckets.
- Final fields, constructor injection, no field injection frameworks — this project has no DI container.

## Selenium / testing conventions

**Locators**
- Prefer stable, user-facing or purpose-built locators in this order: `By.id`, `By.name`, `data-qa` attribute (automationexercise.com exposes `data-qa` on most form fields/buttons, e.g. `By.cssSelector("[data-qa='login-email']")`), `By.linkText`/`By.partialLinkText`, then short CSS selectors. Avoid long XPath or CSS chains tied to DOM depth/classes; use XPath only when text matching is the only option.
- Never use `Thread.sleep` or implicit waits. Use explicit waits (`WebDriverWait` + `ExpectedConditions`, available as `wait` in `BasePage`) for any element that may not be ready yet.
- Declare locators once (as `By` fields in a page object) rather than re-querying strings inline in tests.

**Page Object Model**
- One page object per logical page/component. It exposes user intentions (`login(user, pass)`, `isErrorVisible()`), never raw `WebElement`s or `By`s, to test classes.
- Page objects extend `BasePage` and take a `WebDriver` in their constructor. They must not read `ConfigReader` for anything except the base URL to navigate to.
- Test classes orchestrate page objects and assert; they should read like a script of user actions, not contain Selenium API calls directly.

**Test isolation & lifecycle**
- A fresh `WebDriver` (new browser session, so isolated cookies/storage/cache) is started per test method (`@BeforeEach`/`@AfterEach` in `BaseUiTest`) and always `quit()`. Selenium has no cheap "browser context", so session-per-test is the isolation unit.
- API tests share one `ApiRequestContext` per test class scoped to `api.base.url`; if a test needs isolated auth/session state, create a dedicated context for that test instead of mutating the shared one.
- Don't rely on test execution order. Each test must set up its own preconditions (via API calls or UI actions), not depend on a previous test having run.

**API testing**
- `api.base.url` is `https://automationexercise.com/api/` (note the trailing slash) and endpoint path constants in API clients must NOT start with `/` (e.g. `"productsList"`, not `"/productsList"`). `ApiRequestContext` resolves paths with `URI.resolve`, which treats a path starting with `/` as absolute from the domain root and silently drops the `/api` segment of the base URL, sending requests to the plain website instead of the API. Always check `response.url()` if a request behaves unexpectedly.
- Add one client class per resource/endpoint group under `api/clients`, extending `BaseApiClient`. Expose intention-revealing methods (`createAccount(account)`, `verifyLogin(email, password)`) instead of leaking raw `post("createAccount", ...)` calls into tests.
- Model request/response bodies as records under `api/model`. Deserialize with `BaseApiClient.parse(response, Type.class)` (Gson) rather than manual string parsing.
- automationexercise.com's API always returns HTTP 200 and encodes the real result in the body's `responseCode`/`message` fields (see each `apiNN_.../README.md` under `api/testcases`) — assert on the body, not just the transport status code.
- Several API test cases depend on the account created in API test case 11 (`createAccount`); reuse a shared setup instead of duplicating account-creation logic per test class.

**Assertions**
- Use AssertJ (`assertThat(...)`) for all assertions — fluent, readable failure messages.
- Selenium has no web-first/retrying assertions: when asserting on live page state, wait for the condition first (explicit wait inside the page object, e.g. `isLoaded()`), then assert the returned value with AssertJ.

**Configuration**
- All environment-specific values (base URLs, headless flag, browser choice) live in `src/main/resources/config.properties`, read through `ConfigReader`. Never hardcode a URL or credential in a test or page object.
- Any property can be overridden per-run with `-Dkey=value` without editing the file — use this for local debugging (`-Dheadless=true`) or pointing at a different environment.
- Never commit real secrets/credentials to `config.properties`; use `-D` overrides or environment-specific files that are gitignored if a real target requires auth.

**Naming & structure**
- Test classes: `<Subject>Test` (e.g. `RegisterUserTest`, `VerifyLoginValidTest`), placed inside their `tcNN_<slug>` folder.
- Test methods: descriptive camelCase names stating the scenario and expectation (`registerUserCreatesAndDeletesAccount`), plus a `@DisplayName` for the human-readable version when the method name alone isn't self-explanatory.
- Structure test bodies as given/when/then via blank lines or comments only when it isn't already obvious from short, sequential statements.

## What to avoid

- Don't add hard waits or sleeps to work around flakiness — fix the locator/assertion instead.
- Don't reach for long XPath/CSS chains when an id/name/`data-qa`/link-text locator is available.
- Don't use implicit waits, and don't mix them with explicit waits.
- Don't put Selenium calls directly in test classes — route them through a page object or API client.
- Don't add new dependencies (assertion libraries, HTTP clients, JSON libraries) casually — Selenium + JDK `HttpClient` + JUnit 5 + AssertJ + Gson already cover UI, API, assertions and JSON for this project's scope.
