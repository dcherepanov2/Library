package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.book.Book;

import java.util.List;

public class RecommendedBooksDto {

    private Integer count;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    private List<Book> books;

    public RecommendedBooksDto(List<Book> books) {
        this.books = books;
        this.count = books.size();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
