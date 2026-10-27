package com.swd392.onlineshop.dto;

import com.swd392.onlineshop.enums.Status;
import lombok.*;

/**
 * Dac ta "Products List": filter theo category, status;
 * search theo title hoac brief info.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductFilter {

    private Long categoryId;
    private Status status;
    /** Tu khoa tim trong title hoac briefInfo. */
    private String keyword;
}
