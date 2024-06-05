package com.abc.ecommerce.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}