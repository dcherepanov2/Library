package com.example.MyBookShopApp.exception;

public class UserInsufficientBalance extends Exception {

    public UserInsufficientBalance(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
