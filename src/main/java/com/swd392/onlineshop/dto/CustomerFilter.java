package com.swd392.onlineshop.dto;

import com.swd392.onlineshop.enums.CustomerStatus;
import lombok.*;

/**
 * Dac ta "Customers List": filter theo status;
 * search theo full name, email, mobile.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerFilter {

    private CustomerStatus status;
    private String keyword;
}
