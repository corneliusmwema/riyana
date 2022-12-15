package com.example.registration.onboarding.signup;

import com.example.registration.onboarding.signup.validator.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

@PasswordMatches
public class RegistrationRequest {
    private final String username;
    private final String email;
    private final String password;
    private final String confirmPassword;
}
