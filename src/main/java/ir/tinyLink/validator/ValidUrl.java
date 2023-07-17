package ir.tinyLink.validator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = UrlConstraintValidator.class)
@Target({ElementType.PARAMETER, TYPE, FIELD, ANNOTATION_TYPE})
public @interface ValidUrl {
}
