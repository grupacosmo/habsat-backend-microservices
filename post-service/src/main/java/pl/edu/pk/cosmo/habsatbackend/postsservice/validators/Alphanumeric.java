package pl.edu.pk.cosmo.habsatbackend.postsservice.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AlphanumericValidator.class)
@Documented
public @interface Alphanumeric {
    String message() default "Value must contains only alphanumeric characters, dashes or underscores.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
