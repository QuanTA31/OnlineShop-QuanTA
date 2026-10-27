package com.swd392.onlineshop.entity;

import com.swd392.onlineshop.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Lich su thay doi thong tin customer/contact.
 * Man hinh "Customer Details" yeu cau bang gom: email, full-name, gender,
 * mobile, address, updated by, updated date.
 * Moi dong luu GIA TRI TRUOC KHI SUA.
 */
@Entity
@Table(name = "customer_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_history_customer"))
    private Customer customer;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "full_name", length = 150)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 20)
    private Gender gender;

    @Column(name = "mobile", length = 20)
    private String mobile;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "updated_by", length = 150)
    private String updatedBy;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;
}
