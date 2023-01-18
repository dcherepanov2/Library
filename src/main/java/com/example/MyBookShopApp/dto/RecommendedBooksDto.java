package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.book.Book;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class RecommendedBooksDto implements AopDto{

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("books")
    private List<BookForMainPageDto> books;

    public RecommendedBooksDto(List<Book> books) {
        this.count = books.size();
        List<BookForMainPageDto> bookForMainPageDto = new ArrayList<>();
        for(Book book:books){
            bookForMainPageDto.add(new BookForMainPageDto(book));
        }
        this.books = bookForMainPageDto;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<BookForMainPageDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookForMainPageDto> books) {
        this.books = books;
    }
}
