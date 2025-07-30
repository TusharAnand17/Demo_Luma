Feature: Registration Form Validation

  Scenario: Verify required fields validation
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
      | email              |
      | user@domain        |
      | user@domain,com    |
      | user@.com          |
      | user@domain..com   |
      | user@domain@domain |
      | user@domain.c      |

  Scenario Outline: Verify password strength and format validation
    Given the user is on the registration form
    When the user enters a password "<password>"
    And the user submits the form
    Then the password strength indicator reflects weakness
    And a generic validation error is shown

    Examples:
      | password          |
      | short             |
      | longerthan16chars |
      | onlylowercase     |

  Scenario: Verify confirm password match
    Given the user is on the registration form
    When the user enters a password "Password123"
    And the user enters a different value in Confirm Password "Password321"
    And the user submits the form
    Then a mismatch error message is shown

  Scenario: Verify successful submission
    Given the user is on the registration form
    When the user enters valid values for all required fields
    And the user submits the form
    Then a new account is created
    And a generic success message or redirection occurs

  Scenario: Verify email uniqueness check
    Given the user is on the registration form
    When the user enters an email that is already registered "existing@example.com"
    And the user submits the form
    Then a generic email already exists message is shown

  Scenario: Verify leading/trailing space handling
    Given the user is on the registration form
    When the user enters values with leading/trailing spaces in any input fields
    And the user submits the form
    Then leading/trailing spaces are automatically trimmed before validation and submission

  Scenario Outline: Verify field length and character restrictions
    Given the user is on the registration form
    When the user enters a "<field>" with value "<value>"
    And the user submits the form
    Then a generic error message is shown for exceeding the character limit

    Examples:
      | field     | value                                                                 |
      | First Name| AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA                  |
      | Last Name | BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB                |
      | Email     | verylongemailaddress12345678901234567890123456789012345678901234567890@example.com |
      | Password  | AAAAAAAAAAAAAAAAAA                                                    |

  Scenario Outline: Verify alphabetic character restriction for First Name
    Given the user is on the registration form
    When the user enters a First Name with non-alphabetic characters "<firstName>"
    And the user submits the form
    Then a generic error message is shown for invalid characters

    Examples:
      | firstName |
      | John123   |
      | Jane@     |
      | Mike!     |
      | Alice#    |

  Scenario Outline: Verify alphabetic character restriction for Last Name
    Given the user is on the registration form
    When the user enters a Last Name with non-alphabetic characters "<lastName>"
    And the user submits the form
    Then a generic error message is shown for invalid characters

    Examples:
      | lastName |
      | Doe@!    |
      | Smith#   |
      | Brown$   |
      | Johnson% |

  Scenario: Verify keyboard navigation accessibility for the registration form
    Given the user is on the registration form
    When the user navigates through the form using the keyboard
    Then the user should be able to access all fields and buttons using keyboard navigation