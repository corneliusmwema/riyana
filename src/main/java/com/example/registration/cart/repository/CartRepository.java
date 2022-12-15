package com.example.registration.cart.repository;

import com.example.registration.cart.model.Cart;
import com.example.registration.onboarding.appuser.UserFarmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(UserFarmer user);
}
