package com.gatewaycorps.assignment.workerProcess.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = TrotEventValidators.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrotEventConstraint {
    String message() default "TrotEvent and Time cannot be empty or null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
