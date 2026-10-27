package com.swd392.onlineshop.entity;

import com.swd392.onlineshop.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Bai viet / blog. Chua lam CRUD o giai doan nay, tao san de database
 * phan anh day du Context Diagram.
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

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
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_post_category"))
    private Setting category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_post_author"))
    private User author;

    @Column(name = "brief_info", length = 500)
    private String briefInfo;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

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
