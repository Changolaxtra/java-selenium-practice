---
name: hint
description: Give starting-point hints for implementing a not-yet-implemented test case (UI or API) — which page objects/API clients to reuse or add, and a high-level approach — without writing the actual test code. Use when the user asks for a hint, pointer, or where to start on a test case, given a test case identifier (e.g. TC01 for UI, API07 for API).
user-invocable: true
allowed-tools:
  - Read
  - Grep
  - Glob
  - Bash(find *)
  - Bash(ls *)
---

# /hint — Starting point for a test case implementation

This is a **learning project** (see `CLAUDE.md`): the project owner writes
test bodies themselves as practice. This skill only points them in the right
direction — it must never write the test class, page object methods, or API
client methods for them. Do not use `Edit` or `Write` in this skill under any
circumstances.

Arguments passed: `$ARGUMENTS` — expected to contain a test case identifier:
a `tc` prefix for UI (`TC01`, `tc1`, ...) or an `api` prefix for API (`API07`,
`api7`, ...), case-insensitive.

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

Same parsing as `/check`: prefix (`tc` → suite `ui`, `api` → suite `api`)
determines the suite; zero-pad the number. If the prefix is missing/
unparseable, ask the user and stop.

## 2. Locate the test case folder

Base directory: `src/test/java/com/practice/<suite>/testcases/`, folder
`<prefix><NN>_*`.

- No match → tell the user the TC number doesn't exist for that suite, list
  available `<prefix>NN_*` folders, stop.

## 3. Check implementation status

- If a `*.java` test class already exists in the folder, tell the user this
  test case already has an implementation and suggest `/check` instead to
  review it. Stop unless they explicitly ask for hints anyway despite the
  existing implementation.
- Otherwise (not implemented) → continue.

## 4. Understand what's being asked

Read the folder's `README.md` (description, preconditions, steps, expected
result). Briefly restate the goal in your own words to confirm understanding
— don't just paste the README back.

## 5. Survey what already exists to reuse

- UI: list `src/main/java/com/practice/ui/pages/*.java` and skim which
  existing page objects already cover part of the flow this test case needs
  (e.g. login/signup, add-to-cart, checkout are shared across several UI
  test cases).
- API: list `src/main/java/com/practice/api/clients/*.java` and
  `src/main/java/com/practice/api/model/*.java`; note the shared account
  fixture from API11 (`createAccount`) if this test case depends on a
  created/logged-in user.
- Note the relevant base class (`BaseUiTest`/`BaseApiTest`) lifecycle already
  in place, so the hint doesn't suggest reinventing setup/teardown.

## 6. Give the hint

Provide, as a short outline (not code):

- Which existing page object(s) or API client(s) to reuse as-is.
- Which page object(s)/API client(s) need a **new method** added, with a
  suggested intention-revealing name (e.g. `loginPage.loginWithInvalid
  Credentials(...)`), but not its implementation/body.
- A rough given/when/then shape of the test, phrased at the level of user
  actions or API calls (e.g. "create account via API fixture → attempt
  login with wrong password → assert error message visible"), not locator
  strings or exact assertions.
- Any convention from `CLAUDE.md` particularly relevant to this test case
  (e.g. "use an explicit wait in the page object here since you're
  asserting live UI state", "remember endpoint constants must not start with `/`").

Keep it a *starting point* — enough to unblock, not a full solution. If the
user pushes for more, remind them the point of this project is to write the
implementation themselves, and offer to run `/check` once they have a draft.
