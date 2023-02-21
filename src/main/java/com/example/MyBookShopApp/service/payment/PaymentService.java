package com.example.MyBookShopApp.service.payment;

import com.example.MyBookShopApp.data.book.Book;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface PaymentService {
    String getPaymentUrl(Double sum) throws NoSuchAlgorithmException;
}
