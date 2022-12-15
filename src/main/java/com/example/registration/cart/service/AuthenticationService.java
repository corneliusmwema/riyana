package com.example.registration.cart.service;

import com.example.registration.cart.exception.AuthenticationFailedException;
import com.example.registration.cart.model.AuthenticationToken;
import com.example.registration.cart.repository.TokenRepository;
import com.example.registration.onboarding.appuser.UserFarmer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final TokenRepository tokenRepository;
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(UserFarmer user) {
        return tokenRepository.findByUser(user);
    }
    public UserFarmer getUser(String token){
        final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if(Objects.isNull(authenticationToken))
            return null;
        return authenticationToken.getUser();

    }
    public void authenticate(String token){
        if(Objects.isNull(token))
            throw new AuthenticationFailedException("Token not present");
        if(Objects.isNull(this.getUser(token)))
            throw new AuthenticationFailedException("Token is not valid");

    }
}
