package com.swd392.onlineshop.dto;

import com.swd392.onlineshop.enums.FeedbackStatus;
import lombok.*;

/**
 * Dac ta "Feedbacks List": filter theo status, product, rated star;
 * search theo full name hoac noi dung feedback.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackFilter {

    private FeedbackStatus status;
    private Long productId;
    private Integer ratedStar;
    private String keyword;
}
