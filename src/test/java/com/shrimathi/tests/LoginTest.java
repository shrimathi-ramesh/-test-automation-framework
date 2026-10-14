package com.shrimathi.tests;

import com.shrimathi.base.BaseTest;
import com.shrimathi.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * UI tests for the Login page using Selenium + TestNG + Page Object Model.
 * Covers positive (valid) and negative (invalid) login scenarios.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Valid login should show success message")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("tomsmith", "SuperSecretPassword!");

        String message = loginPage.getFlashMessage();
        Assert.assertTrue(message.contains("You logged into a secure area"),
                "Expected success message but got: " + message);
    }

    // Data-driven: multiple invalid combinations tested from one method
    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"wronguser", "wrongpass"},
                {"tomsmith",  "wrongpass"},
                {"",          ""}
        };
    }

    @Test(dataProvider = "invalidCredentials",
          description = "Invalid login should show an error message")
    public void testInvalidLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(username, password);

        String message = loginPage.getFlashMessage();
        Assert.assertTrue(message.contains("invalid") || message.contains("Your username is invalid!"),
                "Expected an error message but got: " + message);
    }
}
