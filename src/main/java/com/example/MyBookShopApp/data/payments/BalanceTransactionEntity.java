package com.example.MyBookShopApp.data.payments;

import lombok.Data;
import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance_transaction", uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
@Data
public class BalanceTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "user_id",columnDefinition = "INT NOT NULL")
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
