package com.epam.luma.stepdefinitions;

import io.cucumber.java.en.*;
import lombok.extern.slf4j.Slf4j;
import org.example.pages.LoginPage;
import org.example.utils.DriverManager;
import org.testng.Assert;

@Slf4j
public class LoginPageStep {
    LoginPage loginPage = new LoginPage(DriverManager.getDriver(DriverManager.BrowserType.CHROME));

    @Given("The login page is displayed")
    public void the_login_page_is_displayed() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(loginPage.isFormDisplayed());
    }

    @When("the user enters email {string} and password {string}")
    public void the_user_enters_email_and_password(String email, String password) {
        if (email.equalsIgnoreCase("SPACE")) {
            email = "";
        }
        if (password.equalsIgnoreCase("SPACE")) {
            password = "";
        }
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
    }

    @When("the user clicks the {string} button")
    public void the_user_clicks_the_button(String buttonName) {
        if (buttonName.equalsIgnoreCase("Sign In")) {
            loginPage.clickSignIn();
        } else if (buttonName.equalsIgnoreCase("Forget Your Password")) {
            loginPage.clickForgotPassword();
        }
    }

    @Then("the user should be redirected to the home page")
    public void the_user_should_be_redirected_to_the_home_page() {
        Assert.assertTrue(loginPage.isRedirectedToHomePage());
    }

    @Then("the user should see required error {string} and {string}")
    public void theUserShouldSeeRequiredErrorAnd(String emailError, String passWordError) {
        if (!emailError.isBlank()) {
            Assert.assertTrue(loginPage.isEmailErrorDisplayed(emailError),"Expected email error not visible or incorrect");
        }
        if (!passWordError.isBlank()) {
            Assert.assertTrue(loginPage.getPasswordError().equalsIgnoreCase(passWordError),"Expected password error not visible or incorrect");
        }
    }

    @Then("the user should see messages error {string} for email field")
    public void theUserShouldSeeMessagesErrorForEmailField(String expectedMessage) {
        Assert.assertTrue(loginPage.isEmailErrorDisplayed(expectedMessage),"Invalid email error not displayed or incorrect");
    }


    @Then("the user should see {string} error")
    public void the_user_should_see_error(String expectedError) {
        String actualError = loginPage.getErrorMessageFromTop();
        System.out.println(actualError);
        Assert.assertEquals(actualError, expectedError);
    }

    @Then("the email and password fields should be properly aligned with clear labels")
    public void the_fields_should_be_properly_aligned() {
        Assert.assertTrue(loginPage.areFieldLabelsCorrect());
    }

    @When("the user types in the password field")
    public void the_user_types_in_the_password_field() {
        loginPage.enterPassword("abcdefgh123@IJ");
    }

    @Then("the password characters should be masked")
    public void the_password_characters_should_be_masked() {
        Assert.assertTrue(loginPage.isPasswordMasked(), "Password field is not masked.");
    }

    @Then("the user should be redirected to the password recovery page")
    public void the_user_should_be_redirected_to_password_recovery_page() {
        Assert.assertTrue(loginPage.navigateToAccountRecoveryPage());
    }

    @When("the user types email in email field")
    public void the_user_types_email_in_email_field() {
        loginPage.enterEmail("abc@gmail.com");
    }

    @When("the user enters email {string} and password {string} and presses tab to sign in")
    public void the_user_enters_email_and_password_and_tab_sign_in(String email, String password) {
        loginPage.simulateTabKeyNavigation(email, password);
    }

    @Then("the user should see required field messages error {string}")
    public void the_user_should_see_required_field_messages_error(String errorMessage) {
        Assert.assertTrue(loginPage.areRequiredFieldErrorsVisible(errorMessage));
    }

    @Then("the user should see message error {string} for email field")
    public void the_user_should_see_email_field_error(String errorMessage) {
        Assert.assertTrue(loginPage.isEmailErrorDisplayed(errorMessage));
    }


//    @When("the user enters {string} with value {string}")
//    public void the_user_enters_field_with_value(String field, String value) {
//        loginPage.enterFieldValue(field, value);
//    }
//
//    @Then("the {string} value should be trimmed before validation")
//    public void the_field_value_should_be_trimmed(String field) {
//        Assert.assertTrue(loginPage.isTrimmed(field), "Whitespace was not trimmed in " + field);
//    }
}
