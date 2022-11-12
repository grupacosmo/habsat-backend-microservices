package pl.edu.pk.cosmo.habsatbackend.userservice.utils.validators;

import lombok.extern.slf4j.Slf4j;
import pl.edu.pk.cosmo.habsatbackend.userservice.utils.validators.annotations.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Slf4j
public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            String emailRegexPattern = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
        return (s != null) && s.matches(emailRegexPattern);
    }


}
