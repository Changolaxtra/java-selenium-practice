---
name: check
description: Review an already-implemented test case (UI or API) against this project's Selenium/Java best practices and suggest improvements as text only — never edits the code. Use when the user asks to check, review, or critique a test case implementation, given a test case identifier (e.g. TC01 for UI, API07 for API).
user-invocable: true
allowed-tools:
  - Read
  - Grep
  - Glob
  - Bash(find *)
  - Bash(ls *)
---

# /check — Review a test case implementation

This project is a **learning project** (see `CLAUDE.md`). The whole point of
this skill is to help the project owner practice: report findings as text,
never touch the code yourself. Do not use `Edit` or `Write` in this skill
under any circumstances, even if the fix looks trivial.

Arguments passed: `$ARGUMENTS` — expected to contain a test case identifier:
a `tc` prefix for UI (`TC01`, `tc1`, ...) or an `api` prefix for API (`API07`,
`api7`, ...), case-insensitive. A bare number (`01`) is ambiguous without a
prefix.

## 0. Detect the stack

Don't ask which framework is in use and don't assume one — detect it from the
repo you're running in, then apply that stack's best practices:

- Look at `pom.xml` (and, if unclear, the imports in `BaseUiTest`/`BasePage`):
  - `com.microsoft.playwright:playwright` → **Playwright**
  - `org.seleniumhq.selenium:selenium-java` → **Selenium**
  - Anything else → say the stack isn't recognised, fall back to `CLAUDE.md`
    alone, and note the gap.
- `CLAUDE.md` in the repo root is the source of truth for conventions; the
  idioms below are what to look for in each stack.

- **Playwright**: `Page`/`Locator` fields in page objects, user-facing locators
  (`getByRole`/`getByLabel`/`getByText`/`getByTestId`), auto-waiting and web-first
  assertions (`PlaywrightAssertions.assertThat(locator)`), `BrowserContext` per
  test, `APIRequestContext` for API tests.
- **Selenium**: `WebDriver` + `By` fields in page objects, stable locators
  (`By.id`/`By.name`/`data-qa`/link text/short CSS; no long XPath), explicit waits
  only (`WebDriverWait`/`ExpectedConditions` via `BasePage.wait`; no
  `Thread.sleep`, no implicit waits), AssertJ after waiting inside the page object,
  `WebDriver` per test, `ApiRequestContext`/`ApiResponse` (JDK `HttpClient`) for
  API tests.

## 1. Parse arguments

- Extract the prefix (`tc` → suite `ui`, `api` → suite `api`,
  case-insensitive) and the test case number, zero-padded to two digits
  (`1` → `01`, `tc5` → `05`, `api7` → `07`).
- If the prefix is missing/unparseable (e.g. a bare number with no `tc`/`api`),
  ask the user to clarify which suite they mean and stop — don't guess.

## 2. Locate the test case folder

Base directory: `src/test/java/com/practice/<suite>/testcases/`

Find the folder matching `<prefix><NN>_*` under that directory (there is
exactly one per number, e.g. `tc01_register_user` under `ui/testcases/`,
`api07_verify_login_valid` under `api/testcases/`).

- No match → tell the user this TC number doesn't exist for that suite, list
  the available `<prefix>NN_*` folders in that suite so they can pick, and
  stop.
- Multiple matches → this shouldn't happen; report it and stop.

## 3. Check implementation status

List files in the folder. Besides `README.md`, is there a `*.java` test
class?

- **No test class present** → this test case isn't implemented yet. Tell the
  user there's nothing to review, and suggest running `/hint` instead to get
  a starting point. Stop here — do not invent a review of non-existent code.
- **Test class present** → continue.

## 4. Gather context

- Read the folder's `README.md` for the documented steps, preconditions, and
  expected result — the implementation should match this, not some
  re-derived version of the live site.
- Read the test class.
- Follow references to page objects (`src/main/java/com/practice/ui/pages`)
  or API clients/models (`src/main/java/com/practice/api/clients`,
  `src/main/java/com/practice/api/model`) used by the test, and read those
  too. A review of the test class alone, ignoring the page object/API client
  it drives, misses half the picture.
- Read the relevant base class (`BaseUiTest`/`BaseApiTest`, `BasePage`,
  `BaseApiClient`) only if something about lifecycle/inheritance looks off —
  don't re-read it every time for no reason.

## 5. Review against project conventions

Check the code against `CLAUDE.md` in the repo root (Java conventions,
Selenium/testing conventions, naming & structure, what-to-avoid). Focus
especially on:

- **Locators**: stable ones (`By.id`/`By.name`/`data-qa`/link text/short CSS)
  vs brittle long XPath/CSS chains; `By` fields declared once in the page
  object, not re-queried inline in the test.
- **No hard waits**: no `Thread.sleep`, no implicit waits; explicit waits
  (`WebDriverWait`/`ExpectedConditions`) inside page objects.
- **Page Object Model boundary**: test class contains no direct Selenium
  API calls or raw HTTP calls — everything routed through a page object or
  API client exposing intention-revealing methods.
- **Assertions**: AssertJ (`assertThat(...)`) everywhere; for live UI state,
  the page object waits for the condition first, then the test asserts the
  returned value.
- **API-specific**: endpoint path constants don't start with `/`; assertions
  check the response body's `responseCode`/`message`, not just HTTP 200;
  shared account fixture (API11 `createAccount`) reused rather than
  duplicated if this test depends on a logged-in/created user.
- **Test isolation**: doesn't depend on other tests having run; sets up its
  own preconditions.
- **Naming**: class `<Subject>Test`, descriptive camelCase method names,
  `@DisplayName` where the method name alone isn't self-explanatory.
- **Config**: no hardcoded URLs/credentials — goes through `ConfigReader`.
- **README status**: does the `- [ ]`/`- [x]` status line in `README.md`
  match reality? Flag a mismatch, but don't fix it yourself.

## 6. Report

Present findings as a plain-text review, grouped by convention area. For
each finding give the `file:line` and a one-line explanation of what's off
and what the convention says instead. Call out genuine strengths too, not
just problems — this is a review, not just a nitpick list.

End with an explicit reminder: **these are suggestions only** — nothing was
changed, and applying (or rejecting) them is left to the project owner as
practice.
