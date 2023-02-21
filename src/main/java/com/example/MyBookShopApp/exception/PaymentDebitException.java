package com.example.MyBookShopApp.exception;

public class PaymentDebitException extends Exception {

    public PaymentDebitException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
