package com.example.MyBookShopApp.exception;

import com.example.MyBookShopApp.enums.ErrorMessageResponse;

public class ChangeBookException extends Exception{
    public ChangeBookException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
