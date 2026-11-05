package com.practice.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Common behaviour shared by every page object. Page objects should expose
 * user-facing actions and state (e.g. {@code login(user, pass)},
 * {@code isErrorVisible()}) and hide Selenium locators/elements from test
 * classes.
 */
public abstract class BasePage {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    public String title() {
        return driver.getTitle();
    }

    public String url() {
        return driver.getCurrentUrl();
    }
}
