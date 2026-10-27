package com.swd392.onlineshop.repository.specification;

import com.swd392.onlineshop.dto.FeedbackFilter;
import com.swd392.onlineshop.entity.Customer;
import com.swd392.onlineshop.entity.Feedback;
import com.swd392.onlineshop.entity.Product;
import com.swd392.onlineshop.util.DataUtil;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Dac ta man hinh "Feedbacks List":
 * - Filter theo status, product, rated star
 * - Search theo full name cua customer hoac noi dung feedback
 */
public final class FeedbackSpecification {

    private FeedbackSpecification() {
    }

    public static Specification<Feedback> build(FeedbackFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter == null) {
                return cb.conjunction();
            }

            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            if (filter.getProductId() != null) {
                predicates.add(cb.equal(root.get("product").get("id"), filter.getProductId()));
            }
            if (filter.getRatedStar() != null) {
                predicates.add(cb.equal(root.get("ratedStar"), filter.getRatedStar()));
            }
            if (!DataUtil.isBlank(filter.getKeyword())) {
                String pattern = DataUtil.likePattern(filter.getKeyword());
                var customer = root.<Feedback, Customer>join("customer", JoinType.LEFT);
                predicates.add(cb.or(
                        cb.like(cb.lower(customer.get("fullName")), pattern),
                        cb.like(cb.lower(cb.coalesce(root.get("content"), "")), pattern)
                ));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
