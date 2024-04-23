package org.arjunaoverdrive.newsapp.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import org.arjunaoverdrive.newsapp.validation.validators.EmailAlreadyExistsValidator;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailAlreadyExistsValidator.class)
@ReportAsSingleViolation
public @interface EmailAlreadyExists {

    String message() default  "User with this email already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
