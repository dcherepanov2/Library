package com.example.MyBookShopApp.exception;

public class BookException extends Exception{
    public BookException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
