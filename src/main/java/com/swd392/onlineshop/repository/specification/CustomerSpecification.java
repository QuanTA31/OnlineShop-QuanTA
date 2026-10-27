package com.swd392.onlineshop.repository.specification;

import com.swd392.onlineshop.dto.CustomerFilter;
import com.swd392.onlineshop.entity.Customer;
import com.swd392.onlineshop.util.DataUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Dac ta man hinh "Customers List":
 * - Filter theo status
 * - Search theo full name, email, mobile
 */
public final class CustomerSpecification {

    private CustomerSpecification() {
    }

    public static Specification<Customer> build(CustomerFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter == null) {
                return cb.conjunction();
            }

            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            if (!DataUtil.isBlank(filter.getKeyword())) {
                String pattern = DataUtil.likePattern(filter.getKeyword());
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("fullName")), pattern),
                        cb.like(cb.lower(root.get("email")), pattern),
                        cb.like(cb.lower(cb.coalesce(root.get("mobile"), "")), pattern)
                ));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
