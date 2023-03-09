package com.riyana.app.cart.exception;

public class AuthenticationFailedException extends IllegalArgumentException{
    public AuthenticationFailedException(String s) {
        super(s);
    }
}
