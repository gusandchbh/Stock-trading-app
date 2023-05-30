package com.bonqa.bonqa.unit_tests.util;

import bonqa.security.StrongPasswordValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import jakarta.validation.ConstraintValidatorContext;

class StrongPasswordValidatorTest {
  private final StrongPasswordValidator strongPasswordValidator = new StrongPasswordValidator();

  private ConstraintValidatorContext createMockContext() {
    ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
    ConstraintValidatorContext.ConstraintViolationBuilder builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);

    Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString())).thenReturn(builder);
    Mockito.when(builder.addConstraintViolation()).thenReturn(context);

    return context;
  }

  @Test
  void testIsValid_PasswordTooShort() {
    ConstraintValidatorContext context = createMockContext();
    assertFalse(strongPasswordValidator.isValid("Test1!", context));
  }

  @Test
  void testIsValid_NoUpperCase() {
    ConstraintValidatorContext context = createMockContext();
    assertFalse(strongPasswordValidator.isValid("test1234!", context));
  }

  @Test
  void testIsValid_NoLowerCase() {
    ConstraintValidatorContext context = createMockContext();
    assertFalse(strongPasswordValidator.isValid("TEST1234!", context));
  }

  @Test
  void testIsValid_NoDigit() {
    ConstraintValidatorContext context = createMockContext();
    assertFalse(strongPasswordValidator.isValid("TestPassword!", context));
  }

  @Test
  void testIsValid_NoSpecialCharacter() {
    ConstraintValidatorContext context = createMockContext();
    assertFalse(strongPasswordValidator.isValid("TestPassword1", context));
  }

  @Test
  void testIsValid_WhiteSpaceCharacter() {
    ConstraintValidatorContext context = createMockContext();
    assertFalse(strongPasswordValidator.isValid("Test Password1!", context));
  }

  @Test
  void testIsValid_ValidPassword() {
    ConstraintValidatorContext context = createMockContext();
    assertTrue(strongPasswordValidator.isValid("Test1234!", context));
  }
}

