package com.bookstoreapplication.bookstore.user.value_objects;

import com.bookstoreapplication.bookstore.user.exception.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    @DisplayName("Should return true when password is valid - does not have any whitespaces, is not too short, has uppercase, special character and digit")
    void validateRawFormat() {
        String tooShortPassword = "test";
        String whitespacePassword = "te st";
        String noUpperCasePassword = "testpassword";
        String noDigitPassword = "Testpassword";
        String noSpecialCharterPassword = "Testpassword1";
        String validPassword = "Testpassword1!";

        assertThrows(InvalidPasswordException.class, () -> new Password(tooShortPassword));
        assertThrows(InvalidPasswordException.class, () -> new Password(whitespacePassword));
        assertThrows(InvalidPasswordException.class, () -> new Password(noUpperCasePassword));
        assertThrows(InvalidPasswordException.class, () -> new Password(noDigitPassword));
        assertThrows(InvalidPasswordException.class, () -> new Password(noSpecialCharterPassword));
        assertThrows(InvalidPasswordException.class, () -> new Password(noSpecialCharterPassword));
        assertDoesNotThrow(() -> new Password(validPassword));
    }
}