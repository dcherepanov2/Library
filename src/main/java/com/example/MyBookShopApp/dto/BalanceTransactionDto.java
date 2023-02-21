package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Map;

@Data
public class BalanceTransactionDto {
    private Map.Entry<String, String> bookName;
    private String description;

    private Double sum;

    private String date;

    public BalanceTransactionDto(BalanceTransactionEntity transaction, Book book) {
        if(book != null)
            this.bookName = new AbstractMap.SimpleEntry<>(book.getTitle(), book.getSlug());
        if(transaction != null){
            this.description = transaction.getDescription();
            this.sum = transaction.getValue();
            this.date = localDateTimeToReadeableString(transaction.getTime());
        }
    }

    private String localDateTimeToReadeableString(LocalDateTime localDateTime){
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(localDateTime);
    }
}
