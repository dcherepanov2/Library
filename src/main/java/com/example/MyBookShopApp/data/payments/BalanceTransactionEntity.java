package com.example.MyBookShopApp.data.payments;

import lombok.Data;
import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance_transaction", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@Data
public class BalanceTransactionEntity {

    public BalanceTransactionEntity() {
    }

    public BalanceTransactionEntity(Long userId, LocalDateTime time, Double value, Integer bookId, String description) {
        this.userId = userId;
        this.time = time;
        this.value = value;
        this.bookId = bookId;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "user_id", columnDefinition = "INT NOT NULL")
    private Long userId;

    @Column(name = "time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @Column(name = "value", columnDefinition = "INT NOT NULL  DEFAULT 0")
    private Double value;

    @Column(name = "book_id",columnDefinition = "INT NOT NULL")
    private Integer bookId;

    @Column(name = "description",columnDefinition = "TEXT NOT NULL")
    private String description;
}
