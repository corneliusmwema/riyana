package com.riyana.app.cart.repository;

import com.riyana.app.cart.model.AuthenticationToken;
import com.riyana.app.onboarding.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findByUser(AppUser user);
    AuthenticationToken findByToken(String token);
}
