package org.arjunaoverdrive.newsapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NewsFilterValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NewsFilterValid {

    String message() default  "Either authorId or categoryId must be specified!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
