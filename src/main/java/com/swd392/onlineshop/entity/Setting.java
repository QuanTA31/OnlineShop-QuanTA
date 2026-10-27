package com.swd392.onlineshop.entity;

import com.swd392.onlineshop.enums.Status;
import jakarta.persistence.*;
import lombok.*;

/**
 * Bang cau hinh trung tam cua he thong.
 * Chua Product Categories, Post Categories, User Roles, Admin Menu...
 * (theo muc 3.a - Configuring information trong ban requirements)
 */
@Entity
@Table(name = "settings",
        uniqueConstraints = @UniqueConstraint(name = "uk_setting_type_value",
                columnNames = {"type", "value"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Vi du: PRODUCT_CATEGORY, POST_CATEGORY, USER_ROLE, ADMIN_MENU */
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "value", nullable = false, length = 150)
    private String value;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "description", length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private Status status = Status.ACTIVE;
}
