package com.riyana.app.onboarding.Models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class PhoneRequest {
    private final String nickname;
    private final String phone;
    private final String password;
}
