package com.epam.luma.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.RegistrationPage;
import org.example.utils.DriverManager;
import org.testng.Assert;

public class RegistrationFormValidationStep {
    RegistrationPage registrationPage = new RegistrationPage(DriverManager.getDriver(DriverManager.BrowserType.CHROME));

    @Given("the user is on the registration form")
    public void theUserIsOnTheRegistrationForm() {
        registrationPage.navigateToRegistrationPage();
    }

    @When("the user leaves all required fields empty")
    public void theUserLeavesAllRequiredFieldsEmpty() {
        registrationPage.clearAllRequiredFields();
    }

    @When("the user submits the form")
    public void theUserSubmitsTheForm() {
        registrationPage.submitForm();
    }

    @Then("the system highlights all required fields")
    public void theSystemHighlightsAllRequiredFields() {
        Assert.assertTrue(registrationPage.areRequiredFieldsHighlighted());
    }

    @Then("a generic required field validation message is shown")
    public void aGenericRequiredFieldValidationMessageIsShown() {
        Assert.assertTrue(registrationPage.isRequiredFieldValidationMessageDisplayed());
    }

    @When("the user enters an invalid email format {string}")
    public void theUserEntersAnInvalidEmailFormat(String email) {
        registrationPage.enterEmail(email);
    }

    @Then("a generic invalid email format message is displayed")
    public void aGenericInvalidEmailFormatMessageIsDisplayed() {
        Assert.assertTrue(registrationPage.isInvalidEmailFormatMessageDisplayed());
    }

    @When("the user enters a password {string}")
    public void theUserEntersAPassword(String password) {
        registrationPage.enterPassword(password);
    }

    @Then("the password strength indicator reflects weakness")
    public void thePasswordStrengthIndicatorReflectsWeakness() {
        Assert.assertTrue(registrationPage.isPasswordStrengthWeak());
    }

    @Then("a generic validation error is shown")
    public void aGenericValidationErrorIsShown() {
        Assert.assertTrue(registrationPage.isGenericValidationErrorDisplayed());
    }

    @When("the user enters a different value in Confirm Password {string}")
    public void theUserEntersADifferentValueInConfirmPassword(String confirmPassword) {
        registrationPage.enterConfirmPassword(confirmPassword);
    }

    @Then("a mismatch error message is shown")
    public void aMismatchErrorMessageIsShown() {
        Assert.assertTrue(registrationPage.isPasswordMismatchErrorDisplayed());
    }

    @When("the user enters valid values for all required fields")
    public void theUserEntersValidValuesForAllRequiredFields() {
        registrationPage.fillAllRequiredFieldsWithValidData();
    }

    @Then("a new account is created")
    public void aNewAccountIsCreated() {
        Assert.assertTrue(registrationPage.isNewAccountCreated());
    }

    @Then("a generic success message or redirection occurs")
    public void aGenericSuccessMessageOrRedirectionOccurs() {
        Assert.assertTrue(registrationPage.isSuccessMessageOrRedirectionDisplayed());
    }

    @When("the user enters an email that is already registered {string}")
    public void theUserEntersAnEmailThatIsAlreadyRegistered(String email) {
        registrationPage.enterEmail(email);
    }

    @Then("a generic email already exists message is shown")
    public void aGenericEmailAlreadyExistsMessageIsShown() {
        Assert.assertTrue(registrationPage.isEmailAlreadyExistsMessageDisplayed());
    }

    @When("the user enters values with leading/trailing spaces in any input fields")
    public void theUserEntersValuesWithLeadingTrailingSpacesInAnyInputFields() {
        registrationPage.enterValuesWithLeadingTrailingSpaces();
    }

    @Then("leading/trailing spaces are automatically trimmed before validation and submission")
    public void leadingTrailingSpacesAreAutomaticallyTrimmedBeforeValidationAndSubmission() {
        Assert.assertTrue(registrationPage.areLeadingTrailingSpacesTrimmed());
    }

    @When("the user