package com.swd392.onlineshop.dto;

import com.swd392.onlineshop.enums.FeedbackStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Du lieu hien thi cho "Feedback Details".
 * Theo dac ta, Marketing chi xem va doi status -> cac field khac read-only.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDTO {

    private Long id;

    @NotNull(message = "Vui long chon status")
    private FeedbackStatus status;

    // ===== Read-only =====
    private Long customerId;
    private String customerFullName;
    private String customerEmail;
    private String customerMobile;
    private Long productId;
    private String productName;
    private Integer ratedStar;
    private String content;
    private LocalDateTime createdDate;
}
