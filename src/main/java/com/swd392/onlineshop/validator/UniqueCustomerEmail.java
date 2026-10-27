package com.swd392.onlineshop.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Email cua customer phai duy nhat. Dat o muc lop vi can doc them id
 * de bo qua chinh ban ghi dang sua.
 */
@Documented
@Constraint(validatedBy = UniqueCustomerEmailValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCustomerEmail {

    String message() default "Email nay da duoc su dung";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
