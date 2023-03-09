package com.bonqa.bonqa.domain.security;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.passay.*;

import java.util.Arrays;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        PasswordValidator passwordValidator = new PasswordValidator(
                Arrays.asList(
                        new LengthRule(10, 128),
                        //At least one upper case letter
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        //At least one lower case letter
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),
                        //At least one number
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        //At least one special characters
                        new CharacterRule(EnglishCharacterData.Special, 1),
                        new WhitespaceRule()

                )
        );
        RuleResult result = passwordValidator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        //Sending one message each time failed validation.
        constraintValidatorContext.buildConstraintViolationWithTemplate(passwordValidator.getMessages(result).stream().findFirst().get())
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }

}


