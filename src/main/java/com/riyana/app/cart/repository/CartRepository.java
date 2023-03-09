package com.riyana.app.cart.repository;

import com.riyana.app.cart.model.Cart;
import com.riyana.app.onboarding.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(AppUser user);
}
