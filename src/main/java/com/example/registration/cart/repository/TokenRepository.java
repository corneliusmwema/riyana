package com.example.registration.cart.repository;

import com.example.registration.cart.model.AuthenticationToken;
import com.example.registration.onboarding.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findByUser(AppUser user);
    AuthenticationToken findByToken(String token);
}
