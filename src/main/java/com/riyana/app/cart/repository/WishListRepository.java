package com.riyana.app.cart.repository;

import com.riyana.app.cart.model.WishList;
import com.riyana.app.onboarding.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findAllByUserOrderByCreatedDateDesc(AppUser user);
}
