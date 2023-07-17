package ir.tinyLink.validator;


import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.passay.*;
import org.passay.PasswordValidator;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(final ValidPassword arg0) {

    }
    @SneakyThrows
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        Properties props = new Properties();
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream("passay.properties");
        props.load(inputStream);
        MessageResolver resolver = new PropertiesMessageResolver(props);
        PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(

        new LengthRule(8, 16),
        new CharacterRule(EnglishCharacterData.UpperCase, 1),
        new CharacterRule(EnglishCharacterData.LowerCase, 1),
        new CharacterRule(EnglishCharacterData.Digit, 1),
        new CharacterRule(EnglishCharacterData.Special, 1),
        new WhitespaceRule()));
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
}
}