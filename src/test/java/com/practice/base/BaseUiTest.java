package com.practice.base;

import com.practice.config.ConfigReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Base class for UI tests. A fresh {@link WebDriver} (and therefore a fresh
 * browser session with its own cookies/storage) is started for every test
 * method so tests stay isolated from each other. Driver binaries are
 * resolved automatically by Selenium Manager.
 */
public abstract class BaseUiTest {

    protected WebDriver driver;

    @BeforeEach
    void startDriver() {
        driver = createDriver();
    }

    @AfterEach
    void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static WebDriver createDriver() {
        boolean headless = ConfigReader.headless();
        return switch (ConfigReader.browserName()) {
            case "firefox" -> {
                var options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless");
                }
                yield new FirefoxDriver(options);
            }
            case "edge" -> {
                var options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                }
                yield new EdgeDriver(options);
            }
            case "safari" -> new SafariDriver();
            default -> {
                var options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                }
                yield new ChromeDriver(options);
            }
        };
    }
}
