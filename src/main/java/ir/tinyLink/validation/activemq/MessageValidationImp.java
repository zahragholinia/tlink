package ir.tinyLink.validation.activemq;

import ir.pod.podBusinessPanel.model.modelSrv.ValidateBeanSrv;
import ir.pod.podBusinessPanel.validation.activemq.contract.MessageValidation;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MessageValidationImp implements MessageValidation {





    @Override
    public ValidateBeanSrv validateBean(Object obj, Class aClass) {

        if(obj == null)
            new ValidateBeanSrv(false, "خطا: شی نال می باشد.");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> errors = validator.validate(obj);

        System.out.println(errors);

        if(errors.isEmpty())
            return new ValidateBeanSrv(true);

        List<String> errMsg = new ArrayList<>();

        for (ConstraintViolation<Object> err : errors) {
            errMsg.add(err.getMessage());
        }

        return new ValidateBeanSrv(false, String.join(",", errMsg));
    }

    @Override
    public ValidateBeanSrv validateRegex(String pattern, String message, String data) {

        Pattern pt = Pattern.compile(pattern);
        Matcher matcher = pt.matcher(data);

        if(matcher.find())
            return new ValidateBeanSrv(true);

        return new ValidateBeanSrv(false, message);


    }
}
