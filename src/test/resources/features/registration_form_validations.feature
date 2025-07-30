Feature: Registration Form Validations

  Scenario Outline: Verify required fields validation
    Given the user is on the registration form
    When the user leaves all required fields empty
    And the user submits the form
    Then the system highlights all required fields
    And a generic required field validation message is shown

  Scenario Outline: Verify email format validation
    Given the user is on the registration form
    When the user enters an invalid email format "<email>"
    And the user submits the form
    Then a generic invalid email format message is displayed

    Examples:
      | email           |
      | user@domain     |
      | user@domain.    |
      | user@domain..com|

  Scenario Outline: Verify password strength and format
    Given the user is on the registration form
    When the user enters a password "<password>"
    And the user submits the form
    Then the password strength indicator reflects weakness
    And a generic validation error is shown

    Examples:
      | password        |
      | short           |
      | veryveryverylongpassword |
      | alllowercase    |
      | ALLUPPERCASE    |
      | 12345678        |
      | !@#$%^&*        |

  Scenario Outline: Verify confirm password match
    Given the user is on the registration form
    When the user enters a password "<password>"
    And the user enters a different value "<confirm_password>" in Confirm Password
    And the user submits the form
    Then a mismatch error message is shown

    Examples:
      | password  | confirm_password |
      | password1 | password2         |
      | abcdefgh  | hgfedcba          |

  Scenario Outline: Verify successful submission
    Given the user is on the registration form
    When the user enters valid values for all required fields
    And the user submits the form
    Then a new account is created
    And a generic success message or redirection occurs

  Scenario Outline: Verify email uniqueness check
    Given the user is on the registration form
    When the user enters an email "<email>" that is already registered
    And the user submits the form
    Then a generic email already exists message is shown

    Examples:
      | email                |
      | existing@domain.com  |
      | test@domain.com      |

  Scenario Outline: Verify leading/trailing space handling
    Given the user is on the registration form
    When the user enters values with leading/trailing spaces "<field_value>" in any input fields
    And the user submits the form
    Then leading/trailing spaces are automatically trimmed before validation and submission

    Examples:
      | field_value         |
      | " John "            |
      | " Doe "             |
      | " user@domain.com " |

  Scenario Outline: Verify field length and character restrictions
    Given the user is on the registration form
    When the user enters a "<field>" exceeding "<length>" characters
    And the user submits the form
    Then a generic error message is shown for exceeding the character limit

    Examples:
      | field     | length |
      | First Name| 50     |
      | Last Name | 50     |
      | Email     | 100    |
      | Password  | 16     |

  Scenario Outline: Verify alphabetic character restriction
    Given the user is on the registration form
    When the user enters a "<field>" with non-alphabetic characters "<value>"
    And the user submits the form
    Then a generic error message is shown for invalid characters

    Examples:
      | field     | value       |
      | First Name| John123     |
      | Last Name | Doe!@#      |

  Scenario: Verify keyboard navigation accessibility for the registration form
    Given the user is on the registration form
    When the user navigates through the form using keyboard keys
    Then the user should be able to access all input fields and buttons
    And the focus should move sequentially and logically through the form
