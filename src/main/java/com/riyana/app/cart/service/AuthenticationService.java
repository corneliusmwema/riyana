package com.riyana.app.cart.service;

import com.riyana.app.cart.exception.AuthenticationFailedException;
import com.riyana.app.cart.model.AuthenticationToken;
import com.riyana.app.cart.repository.TokenRepository;
import com.riyana.app.onboarding.Entities.AppUser;
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

    public AuthenticationToken getToken(AppUser user) {
        return tokenRepository.findByUser(user);
    }
    public AppUser getUser(String token){
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
