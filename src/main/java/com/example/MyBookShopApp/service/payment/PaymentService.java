package com.example.MyBookShopApp.service.payment;

import java.security.NoSuchAlgorithmException;

public interface PaymentService {
    String getPaymentUrl(Double sum) throws NoSuchAlgorithmException;
}
