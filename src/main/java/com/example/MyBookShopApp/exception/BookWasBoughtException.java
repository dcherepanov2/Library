package com.example.MyBookShopApp.exception;

public class BookWasBoughtException extends Exception {
    public BookWasBoughtException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
