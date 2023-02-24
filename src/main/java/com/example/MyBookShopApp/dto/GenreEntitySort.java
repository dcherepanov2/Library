package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import lombok.ToString;

import java.util.*;

@ToString(of = {"id","child", "books", "booksCount"})
public class GenreEntitySort implements Comparable<GenreEntitySort>{
    private Integer id;

    private final Integer parentId;

    private String slug;

    private String name;

    private List<Book> books;

    public void incrementBooksCount(Integer booksCount) {
        this.booksCount += booksCount;
    }

    private Integer booksCount;

    private final List<GenreEntitySort> child = new ArrayList<>();

    public GenreEntitySort(GenreEntity genreEntity) {
        this.id = genreEntity.getId();
        this.name = genreEntity.getName();
        this.books = genreEntity.getBooks();
        this.booksCount = books.size();
        if(Objects.equals(genreEntity.getId(), genreEntity.getParentId().getId()))
            this.parentId = 0;
        else
            this.parentId = genreEntity.getParentId().getId();
        this.slug = genreEntity.getSlug();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<GenreEntitySort> getChild() {
        return child;
    }

    public Integer getBooksCount() {
        return booksCount;
    }
    @Override
    public int compareTo(GenreEntitySort o) {
        return this.id - o.id;
    }
}

