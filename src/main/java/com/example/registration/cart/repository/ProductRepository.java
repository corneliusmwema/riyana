package com.example.registration.cart.repository;

import com.example.registration.cart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
