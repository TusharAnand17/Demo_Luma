package org.example.pages;

import org.example.utils.ConfigReader;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{
    public LoginPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//label[@for='email']")
    private WebElement emailLabel;

    @FindBy(xpath = "//label[@for='pass']")
    private WebElement passwordLabel;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "pass")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@class = \"action login primary\"]")
    private WebElement signInBtn;

    @FindBy(xpath = "//a[@class = \"action remind\"]")
    private WebElement forgotYourPasswordLink;

    @FindBy(id = "email-error")
    private WebElement emailError;

    @FindBy(id = "pass-error")
    private WebElement passwordError;

    @FindBy(xpath = "//div[@class=\"login-container\"]/div[@class=\"block block-customer-login\"]")
    private WebElement loginForm;

    @FindBy(xpath = "//div[@data-bind = \"html: $parent.prepareMessageForHtml(message.text)\"]")
    private WebElement errorMessageArea;

    public void navigateToLoginPage(){
        driver.get(ConfigReader.getProperty("login.url"));
    }

    public boolean isFormDisplayed(){
        return loginForm.isDisplayed();
    }

    public boolean isRedirectedToHomePage(){
        String actualUrl = driver.getCurrentUrl();
        return actualUrl.equalsIgnoreCase(ConfigReader.getProperty("homePage.url"));
    }

    public void enterEmail(String email) {
        type(emailInput, email);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void clickSignIn() {
        click(signInBtn);
    }

    public void clickForgotPassword() {
        click(forgotYourPasswordLink);
    }

    public String getEmailError() {
        return getText(emailError);
    }

    public String getPasswordError() {
        return getText(passwordError);
    }

    public String getErrorMessageFromTop(){
        return getText(errorMessageArea);
    }

    public boolean isEmailErrorDisplayed(String expectedError) {
        return isValidationErrorVisible(emailError,expectedError);
    }

    public boolean isPasswordErrorDisplayed() {
        return isElementDisplayed(passwordError);
    }

    public boolean areFieldLabelsCorrect() {
        try {
            boolean isEmailLabelCorrect = emailInput.isDisplayed() && emailLabel.getText().toLowerCase().contains("email");
            boolean isPasswordLabelCorrect = passwordLabel.isDisplayed() && passwordLabel.getText().toLowerCase().contains("password");

            return isEmailLabelCorrect && isPasswordLabelCorrect;
        } catch (NoSuchElementException e) {
            sharedLogger.warn("Label not found: " + e.getMessage());
            return false;
        }
    }

    public boolean isPasswordMasked() {
        String typeAttribute = passwordInput.getAttribute("type");
        sharedLogger.info("Password field type attribute: " + typeAttribute);
        return "password".equalsIgnoreCase(typeAttribute);
    }


    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }

    public boolean navigateToAccountRecoveryPage(){
        driver.get(ConfigReader.getProperty("forgotYourPasswordPage.url"));
        return driver.getCurrentUrl().contains("forgotpassword");
    }

    public void simulateTabKeyNavigation(String email,String password){
        Actions actions = new Actions(driver);
        click(emailInput);
        type(emailInput,email);
        actions.keyDown(Keys.TAB).keyUp(Keys.TAB).perform();
        actions.sendKeys(password);
        actions.keyDown(Keys.TAB).keyUp(Keys.TAB).perform();
        actions.click().perform();
    }

    public boolean areRequiredFieldErrorsVisible(String errorMessage){
        boolean result = true;
        if (emailInput.getAttribute("value").trim().isEmpty())
            result &= isValidationErrorVisible(emailError, errorMessage);

        if (passwordInput.getAttribute("value").trim().isEmpty())
            result &= isValidationErrorVisible(passwordError, errorMessage);

        return result;
    }

    // Util method to check if an element is displayed
    private boolean isElementDisplayed(WebElement element) {
        try {
            waitForElementToBeVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            sharedLogger.warn("Element not displayed: " + getElementInfo(element));
            return false;
        }
    }

    private boolean isValidationErrorVisible(WebElement element, String expectedMessageSubstring) {
        try {
            waitForElementToBeVisible(element);
            boolean visible = element.isDisplayed();
            String actualText = getText(element);
            if (!visible || (expectedMessageSubstring != null && !actualText.toLowerCase().contains(expectedMessageSubstring.toLowerCase()))) {
                System.out.printf("Validation error not visible or doesn't match. Actual: %s%n", actualText);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.printf("Error not visible for: %s%n", e.getMessage());
            return false;
        }
    }
}
