package com.swd392.onlineshop.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Rang buoc muc lop: salePrice khong duoc lon hon listPrice.
 * Khong the dien ta bang annotation co san vi phai so sanh 2 field.
 */
@Documented
@Constraint(validatedBy = SalePriceValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SalePriceValid {

    String message() default "Sale price khong duoc lon hon list price";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
