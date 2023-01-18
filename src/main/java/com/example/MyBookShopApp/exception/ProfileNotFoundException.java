package com.example.MyBookShopApp.exception;

public class ProfileNotFoundException extends Exception{
    public ProfileNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
