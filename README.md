# java-selenium-practice

Practice project for testing [automationexercise.com](https://automationexercise.com/) — both its **website (UI)** and its **REST API** — using **Java 21**, **Maven**, and **Selenium WebDriver** (`org.seleniumhq.selenium`). Selenium drives the browser for UI tests; API tests use the JDK's built-in `java.net.http.HttpClient` (Selenium has no API-testing support), so no extra HTTP dependency is needed. This is the Selenium sibling of `java-playwright-practice` — same structure, same test cases.

Test cases are documented one-by-one before being implemented, based on the site's own published test case lists:
- UI: https://automationexercise.com/test_cases (26 cases)
- API: https://automationexercise.com/api_list (14 cases)

Plus two custom smoke tests (`tc00_...` for UI, `api00_...` for API) that sanity-check the framework itself against the real site.

## Prerequisites

- Java 21 (verify with `java -version`)
- Maven 3.9+ (verify with `mvn -version`)

## First-time setup

No driver install step: Selenium Manager (bundled with Selenium) downloads the matching `chromedriver`/`geckodriver`/`msedgedriver` automatically on first run. You only need the browser itself installed (Chrome by default).

## Commands

Run the entire suite (UI + API):

```bash
mvn test
```

Run only the UI (browser) tests:

```bash
mvn test -Dtest="com.practice.ui.testcases.**"
```

Run only the API tests:

```bash
mvn test -Dtest="com.practice.api.testcases.**"
```

Run a single test case by class name:

```bash
mvn test -Dtest=VisitHomePageTest
mvn test -Dtest=OptionsRequestTest
```

Run a single test case by folder/package (useful once a `tcNN_...`/`apiNN_...` folder has more than one test class):

```bash
mvn test -Dtest="com.practice.ui.testcases.tc01_register_user.**"
```

By default, UI tests run with a **visible browser** (`headless=false`) so you can watch each step while practicing:

```bash
mvn test -Dtest=VisitHomePageTest
```

When running the full suite (or many test cases at once), switch to fast/invisible mode:

```bash
mvn test -Dheadless=true
```

Supported `browser` values: `chrome` (default), `firefox`, `edge`, `safari`. Any key in `src/main/resources/config.properties` (base URL, API base URL, browser, headless) can be overridden the same way with `-Dkey=value`, without editing the file.

## Project layout

```
src/main/java/com/practice/
  config/     ConfigReader - typed access to config.properties, overridable via -D flags
  ui/pages/   Page objects (Selenium WebDriver wrappers)
  api/        ApiRequestContext (JDK HttpClient), BaseApiClient, API clients and response models

src/test/java/com/practice/
  base/               BaseUiTest / BaseApiTest - JUnit 5 lifecycle (WebDriver per test, API context)
  ui/testcases/        One package per UI test case from https://automationexercise.com/test_cases
  api/testcases/       One package per API test case from https://automationexercise.com/api_list
```

Each `testcases/tcNN_.../` (UI) or `testcases/apiNN_.../` (API) folder holds a `README.md` describing that test case (description, preconditions, steps, expected result) plus, once implemented, the corresponding `.java` test class living alongside it. Use `base/BaseUiTest` or `base/BaseApiTest` as the parent class, and add page objects / API clients under `src/main/java/com/practice` as needed.

See `CLAUDE.md` for the coding conventions and testing practices this project follows.

## Project skills

Two Claude Code skills (`.claude/skills/`) are set up to help while working through the test cases, each taking a Test Case ID as input — `tc` prefix for UI, `api` prefix for API (e.g. `tc01`, `api07`):

- `/hint <test-case-id>` — brief initial guidance on where to start (which base class/page object/API client to use, what to watch out for), without writing the test for you.
- `/check <test-case-id>` — reviews an already-implemented test case against the `CLAUDE.md` conventions and suggests improvements; read-only, doesn't edit code.
