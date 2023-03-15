package com.example.MyBookShopApp.dto;

import lombok.Data;

import java.util.List;

@Data
public class BalanceTransactionPage implements AopDto {

    private Integer count;

    private List<BalanceTransactionDto> transactions;

    public BalanceTransactionPage(List<BalanceTransactionDto> transactions) {
        this.transactions = transactions;
        this.count = transactions.size();
    }
}
