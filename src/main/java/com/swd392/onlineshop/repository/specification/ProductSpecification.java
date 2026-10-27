package com.swd392.onlineshop.repository.specification;

import com.swd392.onlineshop.dto.ProductFilter;
import com.swd392.onlineshop.entity.Product;
import com.swd392.onlineshop.util.DataUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Dac ta man hinh "Products List":
 * - Filter theo category, status
 * - Search theo title hoac brief info
 */
public final class ProductSpecification {

    private ProductSpecification() {
    }

    public static Specification<Product> build(ProductFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter == null) {
                return cb.conjunction();
            }

            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), filter.getCategoryId()));
            }
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            if (!DataUtil.isBlank(filter.getKeyword())) {
                String pattern = DataUtil.likePattern(filter.getKeyword());
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), pattern),
                        cb.like(cb.lower(cb.coalesce(root.get("briefInfo"), "")), pattern)
                ));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
