package com.swd392.onlineshop.validator;

import com.swd392.onlineshop.dto.ProductDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SalePriceValidator implements ConstraintValidator<SalePriceValid, ProductDTO> {

    @Override
    public boolean isValid(ProductDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getListPrice() == null || dto.getSalePrice() == null) {
            // Cac annotation @NotNull / @PositiveOrZero da lo phan nay
            return true;
        }
        if (dto.getSalePrice().compareTo(dto.getListPrice()) <= 0) {
            return true;
        }
        // Gan loi vao dung o nhap salePrice de Thymeleaf hien thi canh field
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                        context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("salePrice")
                .addConstraintViolation();
        return false;
    }
}
