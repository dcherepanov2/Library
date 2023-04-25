package com.example.MyBookShopApp.data.book.links;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.file.BookFile;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book2file")
@Data
public class Book2File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull
    private Integer id;

    @JoinColumn(name = "book_id")
    @OneToOne
    @NotNull
    private Book book_id;

    @JoinColumn(name = "file_id")
    @OneToOne
    @NotNull
    private BookFile file_id;
}
