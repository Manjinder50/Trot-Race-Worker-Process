package com.gatewaycorps.assignment.workerProcess.validators;

import com.gatewaycorps.assignment.workerProcess.domain.TrotEvent;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class TrotEventValidators implements ConstraintValidator<TrotEventConstraint, TrotEvent> {


    @Override
    public void initialize(TrotEventConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(TrotEvent trotEvent, ConstraintValidatorContext constraintValidatorContext) {
        return isNotBlank(trotEvent.getEvent()) && isNotBlank(String.valueOf(trotEvent.getTime()));
    }
}
