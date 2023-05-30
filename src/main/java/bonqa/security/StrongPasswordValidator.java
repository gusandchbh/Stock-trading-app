package bonqa.security;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.Arrays;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 22),
                // At least one upper case letter
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // At least one lower case letter
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // At least one number
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // At least one special characters
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()));

        PasswordData passwordData = new PasswordData(password);
        RuleResult result = passwordValidator.validate(passwordData);

        if (!result.isValid()) {
            String violationMessage = passwordValidator.getMessages(result).stream()
                    .findFirst()
                    .orElse("Password does not meet the required complexity rules.");
            context.buildConstraintViolationWithTemplate(violationMessage)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }

        return true;
    }
}
