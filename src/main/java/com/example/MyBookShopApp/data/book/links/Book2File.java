package com.example.MyBookShopApp.data.book.links;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book2file")
public class Book2File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;

    @Column(name = "book_id")
    @NotNull
    private Integer book_id;

    @Column(name = "file_id")
    @NotNull
    private Integer file_id;

    public Integer getFile_id() {
        return file_id;
    }

    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }

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
}
