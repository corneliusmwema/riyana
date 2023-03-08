package com.example.registration.cart.exception;

public class ProductNotExistException extends IllegalArgumentException {
    public ProductNotExistException(String s) {
        super(s);
    }
}
