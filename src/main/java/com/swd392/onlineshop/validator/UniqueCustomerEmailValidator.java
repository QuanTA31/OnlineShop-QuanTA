package com.swd392.onlineshop.validator;

import com.swd392.onlineshop.dto.CustomerDTO;
import com.swd392.onlineshop.repository.CustomerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class UniqueCustomerEmailValidator
        implements ConstraintValidator<UniqueCustomerEmail, CustomerDTO> {

    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(CustomerDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getEmail() == null || dto.getEmail().isBlank()) {
            return true; // @NotBlank / @Email da lo
        }
        boolean duplicated = customerRepository.findByEmail(dto.getEmail().trim())
                .filter(existing -> !Objects.equals(existing.getId(), dto.getId()))
                .isPresent();
        if (!duplicated) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                        context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("email")
                .addConstraintViolation();
        return false;
    }
}
