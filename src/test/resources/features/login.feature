Feature: Login Functionality
  As a registered user,
  I want to log into the system using my credentials,
  So that I can access my account securely.

  Background:
    Given The login page is displayed

  Scenario Outline: Successful login with valid credentials
    When the user enters email "<email>" and password "<password>"
    And the user clicks the "Sign In" button
    Then the user should be redirected to the home page

    Examples:
      | email                          | password        |
      | johndoe@abc.com                | Pass@1234       |
      | validuser1@example.com         | ValidPass1!     |
      | validuser2@example.com         | ValidPass1!     |
      | VALIDUSER3@EXAMPLE.COM         | ValidPass1!     |
      | longemail_100chars@ex.com      | ValidPass1!     |
      | validuser5@example.com         | ValidPass1!     |

  Scenario Outline: Invalid login due to missing or malformed input
    When the user enters email "<email>" and password "<password>"
    And the user clicks the "Sign In" button
    Then the user should see required error "<EmailMessage>" and "<PassWordMessage>"

    Examples:
      | email         | password     | EmailMessage                     | PassWordMessage           |
      | SPACE         | SPACE        | This is a required field.        | This is a required field. |
      | SPACE         | passWord123  | This is a required field.        |                           |
      | abc@gmail.com | SPACE        |                                  | This is a required field. |

  Scenario Outline: Trying to login with invalid email
    When the user enters email "<email>" and password "<password>"
    And the user clicks the "Sign In" button
    Then the user should see messages error "<message>" for email field

    Examples:
      | email   | password   | message                                                                |
      | ABCDEFG | Tushar123  | Please enter a valid email address (Ex: johndoe@domain.com).           |

  Scenario Outline: Invalid login due to wrong credentials
    When the user enters email "<email>" and password "<password>"
    And the user clicks the "Sign In" button
    Then the user should see "<expectedResult>" error

    Examples:
      | email                     | password         | expectedResult                                                                                             |
      | validuser@example.com     | WrongPass123     | The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.|
      | notregistered@example.com | SomePassword1!   | The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.|

  Scenario Outline: Login edge cases for input length
    When the user enters email "<email>" and password "<password>"
    And the user clicks the "Sign In" button
    Then the user should see "<expectedResult>" error

    Examples:
      | email                         | password               | expectedResult                                                                                                          |
      | longemail_101chars@ex.com     | ValidPass1!            | The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.             |
      | validuser@example.com         | VeryLongPassword123    | The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.             |

  Scenario: Verify email and password field alignment and labels
    Then the email and password fields should be properly aligned with clear labels

#
#  Scenario: Verify red asterisk on required fields
#    Then required fields should display a red asterisk

  Scenario: Verify password field masking
    When the user types in the password field
    Then the password characters should be masked

  Scenario: Verify "Forget Your Password" link functionality
    And the user clicks the "Forgot Your Password" button
    Then the user should be redirected to the password recovery page
#
#  Scenario Outline: Verify Tab key navigation across login fields
#    When the user enters email "<email>" and password "<password>" and clicks on Sign In using tab
#    Then the user should be redirected to the home page
#
#    Examples:
#      | email                 | password |
#      | validuser@example.com | password |
