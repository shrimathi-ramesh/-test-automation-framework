package com.shrimathi.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model (POM) for the Login page.
 * We keep all the page's locators and actions in ONE place.
 * If the page changes, we only update here — not in every test.
 *
 * Practice site: https://the-internet.herokuapp.com/login
 */
public class LoginPage {

    private final WebDriver driver;
    private final String url = "https://the-internet.herokuapp.com/login";

    // Locators — how we find each element on the page
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton   = By.cssSelector("button[type='submit']");
    private final By flashMessage  = By.id("flash");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Action: open the login page
    public void open() {
        driver.get(url);
    }

    // Action: perform a login with given username and password
    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    // Read the result/flash message shown after login
    public String getFlashMessage() {
        return driver.findElement(flashMessage).getText().trim();
    }
}
