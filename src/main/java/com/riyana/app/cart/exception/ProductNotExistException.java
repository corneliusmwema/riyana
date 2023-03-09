package com.riyana.app.cart.exception;

public class ProductNotExistException extends IllegalArgumentException {
    public ProductNotExistException(String s) {
        super(s);
    }
}
