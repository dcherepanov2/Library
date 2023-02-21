package com.example.MyBookShopApp.service.payment;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import com.example.MyBookShopApp.repo.payment.TransactionRepo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("transactionServiceImpl")
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepo transactionRepo;

    @Autowired
    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Override
    public List<BalanceTransactionEntity> getAllTransactionByUser(JwtUser jwtUser) {
        return transactionRepo.getBalanceTransactionEntitiesByUserId(jwtUser.getId());
    }

    public void debitPayment(JwtUser jwtUser, Double sum) {
        BalanceTransactionEntity transaction = new BalanceTransactionEntity();
        transaction.setUserId(jwtUser.getId());
        transaction.setValue(sum);
        transaction.setTime(LocalDateTime.now());
        transaction.setDescription("Пополнение счета личного кабинета на сумму: "+ sum);
        transaction.setBookId(null);
        transactionRepo.save(transaction);
    }

    @Override
    public void creditPayment(JwtUser jwtUser, Double sum, Book book) {
        BalanceTransactionEntity transaction = new BalanceTransactionEntity();
        transaction.setUserId(jwtUser.getId());
        transaction.setValue(sum);
        transaction.setTime(LocalDateTime.now());
        transaction.setDescription("Покупка книги  "+ book.getTitle() + " на сумму:" + sum);
        transaction.setBookId(book.getId());
        transactionRepo.save(transaction);
    }
}
