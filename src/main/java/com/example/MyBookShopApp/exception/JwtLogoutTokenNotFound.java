package com.example.MyBookShopApp.exception;

public class JwtLogoutTokenNotFound extends Exception {
    public JwtLogoutTokenNotFound(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
