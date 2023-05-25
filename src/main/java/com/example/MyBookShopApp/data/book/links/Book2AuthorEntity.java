package com.example.MyBookShopApp.data.book.links;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book2author")
public class Book2AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book2author_sequence_gen")
    @SequenceGenerator(name = "book2author_sequence_gen", sequenceName = "book2author_sequence", allocationSize = 1, initialValue = 1001)
    @NotNull
    @Column(name = "id",columnDefinition = "INT NOT NULL AUTO_INCREMENT")
    private Integer id;

    @Column(name = "book_id", columnDefinition = "INT NOT NULL")
    private int bookId;

    @Column(name = "authors_id", columnDefinition = "INT NOT NULL")
    private int authorId;

    @Column(name = "sort_index",columnDefinition = "INT NOT NULL  DEFAULT 0")
    private int sortIndex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }
}