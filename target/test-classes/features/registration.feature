Feature: Registration Form Validations
  As a user
  I want to register with valid data
  So that I can create an account successfully

  Background:
    Given The registration form is displayed

  ###########################################
  # Scenario 1: Required Fields Validation (TC001)
  ###########################################
  Scenario Outline: Validate required fields
    When The user leaves required fields "<firstName>", "<lastName>", "<email>", "<password>", "<confirmPassword>"
    And The user submits the form
    Then The user should see required field error messages "<message>"

    Examples:
      | firstName | lastName | email     | password | confirmPassword | message                   |
      |  SPACE    |  SPACE   | SPACE     | SPACE    | SPACE           | This is a required field. |
      |  SPACE    |  Doe     | SPACE     | SPACE    | SPACE           | This is a required field. |
      |  John     |  SPACE   | john@a.com| pass123  | pass123         | This is a required field. |
      |  SPACE    |  SPACE   | john@a.com| pass123  | pass123         | This is a required field. |

  ###########################################
  # Scenario 2: Email Format Validation (TC002)
  ###########################################
  Scenario Outline: Validate invalid email formats
    When The user enters "<email>" in the email field
    And The user submits the form
    Then The user should see an invalid email format error "<message>"

    Examples:
      | email                  | message                                                      |
      | plainaddress           | Please enter a valid email address (Ex: johndoe@domain.com). |
      | @missingusername.com   | Please enter a valid email address (Ex: johndoe@domain.com). |
      | user@.com              | Please enter a valid email address (Ex: johndoe@domain.com). |
      | user@com               | Please enter a valid email address (Ex: johndoe@domain.com). |
      | user@domain..com       | Please enter a valid email address (Ex: johndoe@domain.com). |
      | user@@example.com      | Please enter a valid email address (Ex: johndoe@domain.com). |
      | user@.com.             | Please enter a valid email address (Ex: johndoe@domain.com). |
      | user@domain.c          | Please enter a valid email address (Ex: johndoe@domain.com). |
      | user@domain.toolongtld | Please enter a valid email address (Ex: johndoe@domain.com). |

  ###########################################
  # Scenario: Invalid Password Strength and Format
  ###########################################
  Scenario Outline: Validate invalid password format due to insufficient character classes or invalid length
    When The user enters "<password>" in the Password field
    And The user submits the form
    Then The user should see a password strength error message "<message>"

    Examples:
      | password               | message                                                                                                                                 |
      | password               | Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters. |
      | PASSWORD               | Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters. |
      | 12345678               | Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters. |
      | password!              | Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters. |
      | PASSWORD!              | Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters. |
      | onlylower1             | Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters. |
      | ONLYUPPER!             | Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters. |
      | nospecial1             | Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters. |
      | Pa1!                   | Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.                      |
      | A1@b                   | Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.                      |
      | Short1!                | Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.                      |
      | VeryVeryLongPass123!   | Maximum length of this field must be equal or less than 16 symbols. Leading and trailing spaces will be ignored.                        |

  ###########################################
  # Scenario 4: Confirm Password Mismatch (TC007)
  ###########################################
  Scenario Outline: Validate mismatch between password and confirm password
    When The user enters "<password>" in the Password field
    And The user enters "<confirmPassword>" in the Confirm Password field
    And The user submits the form
    Then The user should see a password mismatch error message "<message>"

    Examples:
      | password     | confirmPassword | message                           |
      | Password1!   | Password2!      | Please enter the same value again |
      | pass@123     | pass@1234       | Please enter the same value again |
      | helloWorld!  | Helloworld!     | Please enter the same value again |

  ###########################################
  # Scenario 5: Email Already Registered (TC009)
  ###########################################
  Scenario Outline: Validate email uniqueness
    When The user enters a registered email "<email>"
    And The user submits the form
    Then The user should see an email already exists error "<message>"

    Examples:
      | email              | message                                                                                            |
      | abc@gmail.com      | There is already an account with this email address. If you are sure that it is your email address |


#  # Scenario 6: Trim Whitespace in Fields (TC010)  ---------------PENDING--------------
#  ###########################################
#  Scenario Outline: Validate trimming of leading and trailing spaces (TC010)
#    When the user enters "<field>" with value <value>
#    And the user submits the form
#    Then the "<field>" value should be trimmed before validation
#
#    Examples:
#      | field      | value                 |
#      | firstName  | " John"               |
#      | lastName   | "Doe "                |
#      | email      | " john@example.com "  |
#      | password   | " Pass@123 "          |


  ###########################################
  # Scenario 7: Invalid Characters in Name (TC011, TC012, TC019, TC020)
  ###########################################
  Scenario Outline: Validate invalid name inputs
    When The user enters "<field>" with value "<value>"
    And The user submits the form
    Then The user should see a generic invalid name error "<message>" for "<field>"

    Examples:
      | field      | value                                            | message                                                                 |
      | firstName  | @John                                            | Only alphabetic characters (with optional hyphen ' or space) should be allowed. |
      | firstName  | John2                                            | Only alphabetic characters (with optional hyphen ' or space) should be allowed. |
      | lastName   | Doe#123                                          | Only alphabetic characters (with optional hyphen ' or space) should be allowed. |
      | firstName  | J*hn$Doe                                         | Only alphabetic characters (with optional hyphen ' or space) should be allowed. |
      | lastName   | D()e                                             | Only alphabetic characters (with optional hyphen ' or space) should be allowed. |
      | firstName  | JonathanJonathanJonathanJonathanJonathan         | Input must not exceed 50 characters                                             |
      | lastName   | LastNameThatExceedsSixtyCharactersExactlyAndMore | Input must not exceed 50 characters                                             |

  ###########################################
  # Scenario 8: Email Length Restriction (TC013)
  ###########################################
  Scenario Outline: Validate email exceeds length limit
    When The user enters "<email>" in the email field
    And The user submits the form
    Then The user should see a generic email length exceeded error "<message>"

    Examples:
      | email                                                                                                   | message                        |
      | verylongemailthatexceedshundredcharacters_abcdefghijabcdefghijabcdefghijabcdefghijabcdefghij@domain.com | Must not exceed 100 characters |

  ###########################################
  # Scenario 9: Valid Email Formats (TC018)
  ###########################################
  Scenario Outline: Validate correct email formats
    When The user enters a valid email "<email>"
    And The user submits the form
    Then The user should not see an email format error

    Examples:
      | email                          |
      | user.name+tag+filter@gmail.com |
      | firstname.lastname@domain.co   |
      | email@sub.domain.com           |
      | user123@domain.org             |
      | test_user-100@mail.co.in       |

  ###########################################
  # Scenario 10: Field Accepts Valid Characters (TC016, TC017)
  ###########################################
  Scenario Outline: Validate first/last name allows valid characters
    When The user enters "<field>" with value "<value>"
    And The user submits the form
    Then The user should not see a name error message for "<field>"

    Examples:
      | field      | value        |
      | firstName  | Anne-Marie   |
      | lastName   | O'Connor     |
      | firstName  | Mary Jane    |
      | lastName   | De la Cruz   |

  ###########################################
  # Scenario 11: Successful Form Submission (TC008)
  ###########################################
  Scenario Outline: Submit valid data and expect success
    When The user enters valid inputs: "<firstName>", "<lastName>", "<email>", "<password>"
    And The user submits the form
    Then The user should be redirected to the account page

    Examples:
      | firstName | lastName | email                | password   |
      | John      | Doe      | johndoe@abc.com      | Pass@1234  |
      | Alice     | Smith    | alice.smith@mail.in  | Strong1!   |
      | User      | One      | validuser1@example.com| ValidPass1!|
      | User      | Two      | validuser2@example.com|ValidPass1!|
      | User      | Three    | VALIDUSER3@EXAMPLE.COM |ValidPass1!|
      | User      | Four     | longemail_100chars@ex.com |ValidPass1!|
      | User      | Five     | validuser5@example.com     | ValidPass1!|

  ###########################################
  # Scenario 12: Max Length Fields Submission (TC021)
  ###########################################
  Scenario Outline: Validate form submits with max length fields
    When The user enters valid inputs with max length possible: "<firstName>", "<lastName>", "<email>", "<password>"
    And The user submits the form
    Then The user should be redirected to the account page

    Examples:
      | firstName                     | lastName                      | email                                     | password    |
      | JonathanJonathanJonathan      | DoeDoeDoeDoeDoeDoeDoeDoeDoe   | longestemailpossiblewithinlimit@example.com | StrongPwd1! |

  ###########################################
  # Scenario 13: Min Length Fields Submission (TC022)
  ###########################################
  Scenario Outline: Validate form submits with minimum allowed lengths
    When The user enters valid inputs with min length possible: "<firstName>", "<lastName>", "<email>", "<password>"
    And The user submits the form
    Then The user should be redirected to the account page

    Examples:
      | firstName | lastName | email   | password |
      | A         | B        | a@b.co  | Abcd@123 |
