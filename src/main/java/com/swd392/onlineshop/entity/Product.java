package com.swd392.onlineshop.entity;

import com.swd392.onlineshop.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * San pham - man hinh "Products List" & "Product Details" cua Marketing.
 * Category tro toi mot Setting co type = PRODUCT_CATEGORY.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "slug", nullable = false, unique = true, length = 250)
    private String slug;

    @Column(name = "thumbnail", length = 500)
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_product_category"))
    private Setting category;

    @Column(name = "brief_info", length = 500)
    private String briefInfo;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity", nullable = false)
    @Builder.Default
    private Integer quantity = 0;

    @Column(name = "list_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal listPrice;

    @Column(name = "sale_price", precision = 15, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "featured", nullable = false)
    @Builder.Default
    private Boolean featured = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private Status status = Status.ACTIVE;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();

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
