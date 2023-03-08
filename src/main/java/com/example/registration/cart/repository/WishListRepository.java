package com.example.registration.cart.repository;

import com.example.registration.cart.model.WishList;
import com.example.registration.onboarding.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findAllByUserOrderByCreatedDateDesc(AppUser user);
}
