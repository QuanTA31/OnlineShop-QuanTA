package com.swd392.onlineshop.entity;

import com.swd392.onlineshop.enums.FeedbackStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Phan hoi cua khach - man hinh "Feedbacks List" & "Feedback Details".
 * product = null nghia la gop y chung, khong gan voi san pham cu the.
 */
@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_feedback_customer"))
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_feedback_product"))
    private Product product;

    @Column(name = "rated_star", nullable = false)
    private Integer ratedStar;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private FeedbackStatus status = FeedbackStatus.PENDING;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @PrePersist
    void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}
