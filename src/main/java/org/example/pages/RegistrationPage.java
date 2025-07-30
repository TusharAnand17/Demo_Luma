package org.example.pages;

import org.example.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage{
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "firstname")
    private WebElement firstNameField;

    @FindBy(id = "lastname")
    private WebElement lastNameField;

    @FindBy(id = "email_address")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "password-confirmation")
    private WebElement confirmPasswordField;

    @FindBy(css = ".action.submit.primary")
    private WebElement submitButton;

    @FindBy(id = "firstname-error")
    private WebElement firstNameError;

    @FindBy(id = "lastname-error")
    private WebElement lastNameError;

    @FindBy(id = "email_address-error")
    private WebElement emailError;

    @FindBy(xpath = "//div[@data-bind = \"html: $parent.prepareMessageForHtml(message.text)\"]")
    private WebElement emailAlreadyExistsError;

    @FindBy(id = "password-error")
    private WebElement passwordError;

    @FindBy(id = "password-confirmation-error")
    private WebElement confirmPasswordError;

    @FindBy(xpath = "//div[@class=\"column main\"]")
    private WebElement registrationForm;

    public void navigateToRegistrationPage() {
        driver.get(ConfigReader.getProperty("registration.url"));
    }

    public boolean isFormDisplayed() {
        return registrationForm.isDisplayed();
    }

    public void fillRegistrationForm(String firstName, String lastName, String email, String password, String confirmPassword) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(emailField, email);
        type(passwordField, password);
        type(confirmPasswordField, confirmPassword);
    }

    public void enterFieldWithValue(String field, String value) {
        value = value.replace("\"", "").trim(); // for quotes in Examples

        switch (field.toLowerCase()) {
            case "firstname" -> type(firstNameField, value);
            case "lastname" -> type(lastNameField, value);
            case "email" -> type(emailField, value);
            case "password" -> type(passwordField, value);
            case "confirmpassword" -> type(confirmPasswordField, value);
            default -> throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }

    public void enterEmail(String email) {
        type(emailField, email);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        type(confirmPasswordField, confirmPassword);
    }

    public void submitForm() {
        click(submitButton);
    }

    public boolean areRequiredFieldErrorsVisible(String errorMessage) {
        boolean result = true;

        if (firstNameField.getAttribute("value").trim().isEmpty())
            result &= isValidationErrorVisible(firstNameError, errorMessage);

        if (lastNameField.getAttribute("value").trim().isEmpty())
            result &= isValidationErrorVisible(lastNameError, errorMessage);

        if (emailField.getAttribute("value").trim().isEmpty())
            result &= isValidationErrorVisible(emailError, errorMessage);

        if (passwordField.getAttribute("value").trim().isEmpty())
            result &= isValidationErrorVisible(passwordError, errorMessage);

        if (confirmPasswordField.getAttribute("value").trim().isEmpty())
            result &= isValidationErrorVisible(confirmPasswordError, errorMessage);

        return result;
    }

    public boolean isEmailFormatErrorVisible(String expectedError) {
        return isValidationErrorVisible(emailError,expectedError);
    }

    public boolean isPasswordStrengthErrorVisible(String expectedError) {
        return isValidationErrorVisible(passwordError,expectedError);
    }

    public boolean isPasswordMismatchErrorVisible(String expectedError) {
        return isValidationErrorVisible(confirmPasswordError,expectedError);
    }

    public boolean isEmailAlreadyExistsErrorVisible(String expectedError) {
        return isValidationErrorVisible(emailAlreadyExistsError,expectedError);
    }

    public boolean isInvalidNameErrorVisible(String expectedError, String field) {
        WebElement errorElement = switch (field.toLowerCase()) {
            case "firstname" -> firstNameError;
            case "lastname" -> lastNameError;
            default -> throw new IllegalArgumentException("Unsupported field: " + field);
        };

        return isValidationErrorVisible(errorElement, expectedError);
    }

    public boolean isEmailLengthExceededErrorVisible(String expectedError) {
        return isValidationErrorVisible(emailError,expectedError);
    }


    public boolean isNameErrorVisible(String field) {
        return isInvalidNameErrorVisible(null,field);
    }

    public boolean isSuccessRedirect() {
        return wait.until(driver -> {
            String url = driver.getCurrentUrl();
            return url.contains("account");
        });
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