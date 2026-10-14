# UI + API Test Automation Framework

A test automation framework built with **Java, Selenium, TestNG, and REST Assured**, demonstrating UI and API testing using the Page Object Model (POM) design pattern.

## 🧰 Tech Stack
| Purpose | Tool |
|---|---|
| Language | Java 21 |
| Build tool | Maven |
| UI automation | Selenium WebDriver 4 |
| Test framework | TestNG |
| API testing | REST Assured |
| Driver management | WebDriverManager |
| Design pattern | Page Object Model (POM) |

## 📁 Project Structure
```
test-automation-framework/
├── pom.xml                         # Maven dependencies & build config
└── src/test/
    ├── java/com/shrimathi/
    │   ├── base/   BaseTest.java    # Browser setup & teardown
    │   ├── pages/  LoginPage.java   # Page Object (locators + actions)
    │   └── tests/  LoginTest.java   # UI tests (positive + data-driven negative)
    │                ApiTest.java    # API tests (REST Assured)
    └── resources/  testng.xml       # TestNG suite configuration
```

## ✅ What it covers
- **UI testing** — valid and invalid login scenarios on a demo web app
- **Data-driven testing** — multiple invalid-credential sets via TestNG `@DataProvider`
- **API testing** — GET requests with status-code and response-body validation
- **Page Object Model** — page locators and actions kept separate from tests for maintainability
- **Automatic driver setup** — WebDriverManager resolves the browser driver
- **Headless execution** — runs in CI without a visible browser window

## ▶️ How to run
```bash
# Prerequisites: Java 21, Maven, Chrome browser
mvn test
```
Runs the full suite defined in `src/test/resources/testng.xml`.

## 📊 Sample result
```
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
```
- 1 valid-login UI test
- 3 invalid-login UI tests (data-driven)
- 2 API tests (200 success + 404 not-found)

## 🌐 Applications under test
- UI: https://the-internet.herokuapp.com/login (public practice site)
- API: https://reqres.in (public demo REST API)

## 👩‍💻 Author
**Shrimathi Ramesh** — QA Engineer
[LinkedIn](https://www.linkedin.com/in/shrimathi-ramesh-9451aa30b/) · [GitHub](https://github.com/shrimathi-ramesh)
