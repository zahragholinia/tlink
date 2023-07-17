package ir.tinyLink.validator;

import ir.tinyLink.message.LinkMessage;
import lombok.SneakyThrows;
import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlConstraintValidator implements ConstraintValidator<ValidUrl, String> {
    @Override
    public void initialize(final ValidUrl arg0) {

    }

    @SneakyThrows
    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {

        UrlValidator validator = new UrlValidator();
        if (validator.isValid(url))
            return true;
        context.buildConstraintViolationWithTemplate(LinkMessage.errorLinkInvalid(url))
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;

    }
}
