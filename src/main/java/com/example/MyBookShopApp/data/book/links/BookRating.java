package com.example.MyBookShopApp.data.book.links;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rating_book")
public class BookRating {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "book_id")
    @NotNull
    private Integer book_id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "value")
    @NotNull
    private Integer value;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}