package com.swd392.onlineshop.entity;

import com.swd392.onlineshop.enums.Gender;
import com.swd392.onlineshop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Don hang. Ten bang phai la "orders" vi ORDER la tu khoa SQL.
 * Chua lam CRUD o giai doan nay.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_order_customer"))
    private Customer customer;

    /** Sale duoc gan xu ly don hang nay. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_sale_id", foreignKey = @ForeignKey(name = "fk_order_sale"))
    private User assignedSale;

    @Column(name = "total_cost", nullable = false, precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal totalCost = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private OrderStatus status = OrderStatus.NEW;

    // ===== Thong tin nguoi nhan =====
    @Column(name = "receiver_full_name", length = 150)
    private String receiverFullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "receiver_gender", length = 20)
    private Gender receiverGender;

    @Column(name = "receiver_email", length = 150)
    private String receiverEmail;

    @Column(name = "receiver_mobile", length = 20)
    private String receiverMobile;

    @Column(name = "receiver_address", length = 500)
    private String receiverAddress;

    @Column(name = "notes", length = 500)
    private String notes;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderDetail> details = new ArrayList<>();

    @PrePersist
    void onCreate() {
        if (this.orderDate == null) {
            this.orderDate = LocalDateTime.now();
        }
    }
}
