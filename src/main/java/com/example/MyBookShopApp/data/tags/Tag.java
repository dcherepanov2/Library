package com.example.MyBookShopApp.data.tags;

import com.example.MyBookShopApp.data.book.Book;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull
    private Integer id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "slug")
    @NotNull
    private String slug;

    @Column(name = "size")
    @NotNull
    private Integer size;

    @Column(name = "click", columnDefinition = "DEFAULT VALUE 0")
    @NotNull
    private Integer tagClicks;

    @ManyToMany
    @JoinTable(
            name = "book2tag",
            joinColumns = @JoinColumn(name = "tag_id")
            , inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    List<Book> books = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Integer getTagClicks() {
        return tagClicks;
    }

    public void setTagClicks(Integer tagClicks) {
        this.tagClicks = tagClicks;
    }

}
