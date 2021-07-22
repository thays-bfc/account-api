package com.bank.api.account.util.validator;

import com.bank.api.account.util.message.SystemConstants;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CpfValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfConstraint {
    String message() default SystemConstants.Customer.VALID_CPF;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
