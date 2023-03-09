package com.bonqa.bonqa.unit_tests;

import com.bonqa.bonqa.domain.security.StrongPasswordValidator;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StrongPasswordValidatorTest {


    private final StrongPasswordValidator validator = new StrongPasswordValidator();
    @Test
    void validate_GivenPasswordContainingSpecialCharacter_ShouldReturnFalse() {
        // Arrange
        String validPassword = "MySuperStrongPassword123!@#";

        // Act
        boolean result = validator.isValid(validPassword, null);

        // Assert
        assertTrue(result);
    }

    @Test
    void validate_GivenWeakPassword_ShouldReturnFalse() {
        // Arrange
        String weakPassword = "password123";

        // Act
        boolean result = validator.isValid(weakPassword, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void validate_GivenEmptyPassword_ShouldReturnFalse() {
        // Arrange
        String emptyPassword = "";

        // Act
        boolean result = validator.isValid(emptyPassword, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void validate_GivenInvalidPasswordWithoutUppercase_ShouldReturnFalse() {
        // Arrange
        String invalidPassword = "weakpassword123!@#";

        // Act
        boolean result = validator.isValid(invalidPassword, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void validate_GivenInvalidPasswordWithoutLowercase_ShouldReturnFalse() {
        // Arrange
        String invalidPassword = "WEAKPASSWORD123!@#";

        // Act
        boolean result = validator.isValid(invalidPassword, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void validate_GivenInvalidPasswordWithoutNumber_ShouldReturnFalse() {
        // Arrange
        String invalidPassword = "WeakPassword!@#";

        // Act
        boolean result = validator.isValid(invalidPassword, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void validate_GivenPasswordWithMoreThan16Characters_ShouldReturnFalse() {
        // Arrange
        String invalidPassword = "MySuperStrongPasswordWithMoreThan16Characters";

        // Act
        boolean result = validator.isValid(invalidPassword, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void validate_GivenValidPassword_ShouldReturnTrue() {
        // Arrange
        String validPassword = "MyP@ssw0rd123";

        // Act
        boolean result = validator.isValid(validPassword, null);

        // Assert
        assertTrue(result);
    }

    @Test
    void validate_GivenPasswordWithExactly8Characters_ShouldReturnFalse() {
        // Arrange
        String invalidPassword = "password";

        // Act
        boolean result = validator.isValid(invalidPassword, null);

        // Assert
        assertFalse(result);
    }



}
