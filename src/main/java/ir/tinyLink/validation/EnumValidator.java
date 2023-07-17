package ir.tinyLink.validation;

import ir.pod.podBusinessPanel.annotation.ValidateEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mujtaba Hossaini
 * @project PodBusinessPanel
 * @created 19/02/2023 - 14:43
 * @email mujtaba.hossaini94@gmail.com
 */
public class EnumValidator implements ConstraintValidator<ValidateEnum, Enum<?>> {

    private List<String> fieldNames = new ArrayList<>();
    private Class enumClass;
    private boolean nullable;

    @Override
    public void initialize(ValidateEnum constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
        List<Field> fields = Arrays.asList(constraintAnnotation.enumClass().getDeclaredFields());
        nullable = constraintAnnotation.nullable();
        fieldNames.addAll(fields.stream().map(Field::getName).collect(Collectors.toList()));
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if(nullable && value == null)
            return true;
        else if(!nullable && value == null)
            return false;
        return  enumClass == value.getDeclaringClass() && fieldNames.contains(value.name());
    }
}
