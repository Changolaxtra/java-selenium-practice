package com.practice.ui.pages;

import com.practice.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private final By homeNavLink = By.cssSelector("ul.navbar-nav a[href='/']");
    private final By slogan = By.xpath("(//*[contains(text(),'Full-Fledged practice website for Automation Engineers')])[1]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(ConfigReader.baseUrl());
        return this;
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(homeNavLink));
            wait.until(ExpectedConditions.visibilityOfElementLocated(slogan));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
