package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
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
        for (Book book : books) {
            book.setBookRatings(null);
            bookForMainPageDto.add(new BookForMainPageDto(book));
        }
        this.books = bookForMainPageDto;
    }

    public RecommendedBooksDto(List<Book> books, String cartContents, String keptContents, List<Book2UserEntity> books2User) {
        this.count = books.size();
        List<BookForMainPageDto> bookForMainPageDto = new ArrayList<>();
        for (Book book : books) {
            book.setBookRatings(null);
            bookForMainPageDto.add(new BookForMainPageDto(book, keptContents, cartContents, books2User));
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
