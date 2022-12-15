package com.example.registration.onboarding.signin;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
public class SigninRequest {
    @NotNull @Length(min = 10, max = 15)
    private String phone;

    @NotNull @Length(min = 5, max = 16)
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone= phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
