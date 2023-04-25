package com.example.MyBookShopApp.data.book.links;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book2genre")
public class Book2GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "book_id",columnDefinition = "INT NOT NULL")
    @NotNull
    private int bookId;

    @Column(name = "genre_id",columnDefinition = "INT NOT NULL")
    @NotNull
    private int genreId;
}
