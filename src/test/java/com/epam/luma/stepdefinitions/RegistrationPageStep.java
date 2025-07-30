package com.epam.luma.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.RegistrationPage;
import org.example.utils.DriverManager;
import org.testng.Assert;

public class RegistrationPageStep {
    RegistrationPage registrationPage = new RegistrationPage(DriverManager.getDriver(DriverManager.BrowserType.CHROME));

    @Given("The registration form is displayed")
    public void theRegistrationFormIsDisplayed() {
        registrationPage.navigateToRegistrationPage();
        Assert.assertTrue(registrationPage.isFormDisplayed());
    }

    private String parseField(String value) {
        return value.equalsIgnoreCase("SPACE") ? "     " : value;
    }

    @When("The user leaves required fields {string}, {string}, {string}, {string}, {string}")
    public void theUserLeavesRequiredFields(String firstName, String lastName, String email, String password, String confirmPassword) {
        registrationPage.fillRegistrationForm(
                parseField(firstName),
                parseField(lastName),
                parseField(email),
                parseField(password),
                parseField(confirmPassword)
        );
    }

    @When("The user submits the form")
    public void theUserSubmitsTheForm() {
        registrationPage.submitForm();
    }

    @Then("The user should see required field error messages {string}")
    public void theUserShouldSeeRequiredFieldErrorMessages(String errorMessage) {
        Assert.assertTrue(registrationPage.areRequiredFieldErrorsVisible(errorMessage));
    }

    @When("The user enters {string} in the email field")
    public void theUserEntersInTheEmailField(String email) {
        registrationPage.enterEmail(email);
    }

    @Then("The user should see an invalid email format error {string}")
    public void theUserShouldSeeAnInvalidEmailFormatError(String errorMessage) {
        Assert.assertTrue(registrationPage.isEmailFormatErrorVisible(errorMessage));
    }

    @When("The user enters {string} in the Password field")
    public void theUserEntersInThePasswordField(String password) {
        registrationPage.enterPassword(password);
    }

    @Then("The user should see a password strength error message {string}")
    public void theUserShouldSeeAPasswordStrengthErrorMessage(String errorMessage) {
        Assert.assertTrue(registrationPage.isPasswordStrengthErrorVisible(errorMessage));
    }

    @When("The user enters {string} in the Confirm Password field")
    public void theUserEntersInTheConfirmPasswordField(String confirmPassword) {
        registrationPage.enterConfirmPassword(confirmPassword);
    }

    @Then("The user should see a password mismatch error message {string}")
    public void theUserShouldSeeAPasswordMismatchErrorMessage(String expectedMessage) {
        Assert.assertTrue(registrationPage.isPasswordMismatchErrorVisible(expectedMessage));
    }

    @When("The user enters a registered email {string}")
    public void theUserEntersARegisteredEmail(String email) {
        registrationPage.fillRegistrationForm("Random","Random",email,"Password123","Password123");
    }

    @Then("The user should see an email already exists error {string}")
    public void theUserShouldSeeAnEmailAlreadyExistsError(String errorMessage) {
        Assert.assertTrue(registrationPage.isEmailAlreadyExistsErrorVisible(errorMessage));
    }

    @Then("The user should see a generic invalid name error {string} for {string}")
    public void theUserShouldSeeAGenericInvalidNameErrorFor(String expectedMessage, String field) {
        Assert.assertTrue(registrationPage.isInvalidNameErrorVisible(expectedMessage, field));
    }

    @Then("The user should see a generic email length exceeded error {string}")
    public void theUserShouldSeeAGenericEmailLengthExceededError(String expectedError) {
        Assert.assertTrue(registrationPage.isEmailLengthExceededErrorVisible(expectedError));
    }

    @When("The user enters a valid email {string}")
    public void theUserEntersAValidEmail(String email) {
        registrationPage.enterEmail(email);
    }

    @Then("The user should not see an email format error")
    public void theUserShouldNotSeeAnEmailFormatError() {
        Assert.assertFalse(registrationPage.isEmailFormatErrorVisible(null));
    }

    @Then("The user should not see a name error message for {string}")
    public void theUserShouldNotSeeANameErrorMessageFor(String field) {
        Assert.assertFalse(registrationPage.isNameErrorVisible(field));
    }

    @When("The user enters valid inputs: {string}, {string}, {string}, {string}")
    public void theUserEntersValidInputs(String firstName, String lastName, String email, String password) {
        registrationPage.fillRegistrationForm(firstName, lastName, email, password, password);
    }

    @Then("The user should be redirected to the account page")
    public void theUserShouldBeRedirectedToTheAccountPage() {
        Assert.assertTrue(registrationPage.isSuccessRedirect());
    }

    @When("The user enters valid inputs with max length possible: {string}, {string}, {string}, {string}")
    public void theUserEntersValidInputsWithMaxLengthPossible(String firstName, String lastName, String email, String password) {
        registrationPage.fillRegistrationForm(firstName, lastName, email, password, password);
    }

    @When("The user enters valid inputs with min length possible: {string}, {string}, {string}, {string}")
    public void theUserEntersValidInputsWithMinLengthPossible(String firstName, String lastName, String email, String password) {
        registrationPage.fillRegistrationForm(firstName, lastName, email, password, password);
    }

    @When("The user enters {string} with value {string}")
    public void theUserEntersWithValue(String field, String value) {
        registrationPage.enterFieldWithValue(field, value);
    }
}
