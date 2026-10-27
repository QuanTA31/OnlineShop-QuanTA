package com.swd392.onlineshop.entity;

import com.swd392.onlineshop.enums.CustomerStatus;
import com.swd392.onlineshop.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer / Contact - man hinh "Customers List" & "Customer Details" cua Marketing.
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 20)
    private Gender gender;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "mobile", length = 20)
    private String mobile;

    @Column(name = "address", length = 500)
    private String address;

    /** He thong tu sinh: CONTACT -> POTENTIAL -> CUSTOMER. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private CustomerStatus status = CustomerStatus.CONTACT;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("updatedDate DESC")
    @Builder.Default
    private List<CustomerHistory> histories = new ArrayList<>();

    @PrePersist
    void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = this.createdDate;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
