package com.swd392.onlineshop.entity;

import com.swd392.onlineshop.enums.Status;
import jakarta.persistence.*;
import lombok.*;

/**
 * Slider tren trang chu. Chua lam CRUD o giai doan nay.
 */
@Entity
@Table(name = "sliders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "image", length = 500)
    private String image;

    @Column(name = "backlink", length = 500)
    private String backlink;

    @Column(name = "notes", length = 500)
    private String notes;

    @Column(name = "display_order")
    @Builder.Default
    private Integer displayOrder = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private Status status = Status.ACTIVE;
}
