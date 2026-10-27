package com.swd392.onlineshop.entity;

import com.swd392.onlineshop.enums.Gender;
import com.swd392.onlineshop.enums.Status;
import jakarta.persistence.*;
import lombok.*;

/**
 * Nguoi dung he thong (Marketing, Sale, Sale Manager, Admin, Customer da dang ky).
 * Role tro toi mot Setting co type = USER_ROLE.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

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

    /** Giu lai cho phan authentication se bo sung sau. */
    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "avatar", length = 500)
    private String avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_user_role"))
    private Setting role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private Status status = Status.ACTIVE;

    @Column(name = "verified", nullable = false)
    @Builder.Default
    private Boolean verified = Boolean.FALSE;
}
