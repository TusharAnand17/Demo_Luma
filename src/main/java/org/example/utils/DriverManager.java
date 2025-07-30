package org.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public enum BrowserType {
        CHROME,
        EDGE,
        FIREFOX
    }


    public static WebDriver getDriver(BrowserType browser) {
        if (driverThread.get() == null) {
            driverThread.set(createDriver(browser));
        }
        return driverThread.get();
    }


    private static WebDriver createDriver(BrowserType browser) {
        switch (browser) {
            case CHROME -> {
                return new ChromeDriver();
            }
            case EDGE -> {
                return new EdgeDriver();
            }
            case FIREFOX -> {
                return new FirefoxDriver();
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }


    public static void quitDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
        }
    }
}