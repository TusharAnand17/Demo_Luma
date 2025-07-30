package com.epam.luma.hooks;

import org.example.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

public class UIHooks {

    private WebDriver driver;


    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome").toUpperCase();
        driver = DriverManager.getDriver(DriverManager.BrowserType.valueOf(browser));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }


    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}