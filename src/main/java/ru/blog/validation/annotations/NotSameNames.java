package ru.blog.validation.annotations;

import ru.blog.validation.validators.NamesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NamesValidator.class)
public @interface NotSameNames {
    String message() default "same names";

    String[] names() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
