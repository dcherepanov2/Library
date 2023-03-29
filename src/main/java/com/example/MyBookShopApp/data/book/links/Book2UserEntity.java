package com.example.MyBookShopApp.data.book.links;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "book2user")
public class Book2UserEntity {
    public Book2UserEntity() {
    }

    public Book2UserEntity(LocalDateTime time, int typeId, int bookId, int userId) {
        this.time = time;
        this.typeId = typeId;
        this.bookId = bookId;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @Column(name = "type_id", columnDefinition = "INT NOT NULL")
    private Integer typeId;

    @Column(name = "book_id", columnDefinition = "INT NOT NULL")
    private Integer bookId;

    @Column(name = "user_id", columnDefinition = "INT NOT NULL")
    private Integer userId;

}
