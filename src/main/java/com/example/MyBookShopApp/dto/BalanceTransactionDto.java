package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Map;

@Data
public class BalanceTransactionDto {
    @JsonIgnore
    private Map.Entry<String, String> bookName;
    private String description;

    private Integer value;

    private String time;

    public BalanceTransactionDto(BalanceTransactionEntity transaction, Book book) {
        if (book != null)
            this.bookName = new AbstractMap.SimpleEntry<>(book.getTitle(), book.getSlug());
        if (transaction != null) {
            this.description = transaction.getDescription();
            this.value = (int) Math.floor(transaction.getValue());
            this.time = localDateTimeToReadeableString(transaction.getTime());
        }
    }

    public BalanceTransactionDto(BalanceTransactionEntity transaction) {
        if (transaction != null) {
            this.description = transaction.getDescription();
            this.value = (int) Math.floor(transaction.getValue());
            Instant instant = transaction.getTime().atZone(ZoneId.systemDefault()).toInstant();
            this.time = String.valueOf(instant.toEpochMilli());
        }
    }

    private String localDateTimeToReadeableString(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(localDateTime);
    }
}
