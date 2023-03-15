package com.example.MyBookShopApp.service.payment;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import com.example.MyBookShopApp.security.jwt.JwtUser;

import java.util.List;

public interface TransactionService {
    List<BalanceTransactionEntity> getAllTransactionByUser(JwtUser jwtUser, Integer offset, Integer limit);

    void debitPayment(JwtUser jwtUser, Double sum);

    void creditPayment(JwtUser jwtUser, Double sum, Book book);
}
