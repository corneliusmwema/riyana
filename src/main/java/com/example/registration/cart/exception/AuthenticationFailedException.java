package com.example.registration.cart.exception;

public class AuthenticationFailedException extends IllegalArgumentException{
    public AuthenticationFailedException(String s) {
        super(s);
    }
}
