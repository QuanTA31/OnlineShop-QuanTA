package com.swd392.onlineshop.repository;

import com.swd392.onlineshop.entity.Order;
import com.swd392.onlineshop.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository
        extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    long countByCustomerId(Long customerId);

    long countByCustomerIdAndStatus(Long customerId, OrderStatus status);
}
