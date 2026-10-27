package com.swd392.onlineshop.repository;

import com.swd392.onlineshop.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
