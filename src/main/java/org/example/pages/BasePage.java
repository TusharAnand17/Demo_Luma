package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected final WebDriverWait wait;


    protected static final Logger sharedLogger = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait =  new WebDriverWait(driver, Duration.ofSeconds(12));
        sharedLogger.info("Initialized BasePage.");
    }

    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void click(WebElement element) {
        waitForElementToBeClickable(element);
        sharedLogger.info("Clicking on element: " + getElementInfo(element));
        element.click();
    }

    public void type(WebElement element, String text) {
        waitForElementToBeVisible(element);
        sharedLogger.info("Typing '" + text + "' into element: " + getElementInfo(element));
        element.clear();
        element.sendKeys(text.trim());
    }

    public String getText(WebElement element) {
        waitForElementToBeVisible(element);
        sharedLogger.info("Retrieving text from element: " + getElementInfo(element));
        return element.getText();
    }

    protected String getElementInfo(WebElement element) {
        try {
            return "tag=" + element.getTagName() + ", displayed=" + element.isDisplayed() + ", enabled=" + element.isEnabled();
        } catch (Exception e) {
            return "Unknown Element Info";
        }
    }
}