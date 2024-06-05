package com.abc.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.ecommerce.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

	List<Product> findAllByIdInOrderById(List<Integer> productIds);

}
