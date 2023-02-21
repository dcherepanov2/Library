package com.example.MyBookShopApp.repo.payment;

import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<BalanceTransactionEntity, Integer> {
    List<BalanceTransactionEntity> getBalanceTransactionEntitiesByUserId(Long userId);
}
