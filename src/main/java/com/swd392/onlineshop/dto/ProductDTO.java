package com.swd392.onlineshop.dto;

import com.swd392.onlineshop.enums.Status;
import com.swd392.onlineshop.validator.SalePriceValid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Du lieu binding cho form "Product Details".
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SalePriceValid
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Title khong duoc de trong")
    @Size(max = 200, message = "Title toi da 200 ky tu")
    private String title;

    @Size(max = 500, message = "Thumbnail toi da 500 ky tu")
    private String thumbnail;

    @NotNull(message = "Vui long chon category")
    private Long categoryId;

    @Size(max = 500, message = "Brief info toi da 500 ky tu")
    private String briefInfo;

    private String description;

    @NotNull(message = "Quantity khong duoc de trong")
    @PositiveOrZero(message = "Quantity phai >= 0")
    private Integer quantity;

    @NotNull(message = "List price khong duoc de trong")
    @PositiveOrZero(message = "List price phai >= 0")
    private BigDecimal listPrice;

    @PositiveOrZero(message = "Sale price phai >= 0")
    private BigDecimal salePrice;

    @Builder.Default
    private Boolean featured = Boolean.FALSE;

    @NotNull(message = "Vui long chon status")
    @Builder.Default
    private Status status = Status.ACTIVE;

    // ===== Chi de hien thi, khong binding tu form =====
    private String slug;
    private String categoryName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
