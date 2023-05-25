package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;

import java.util.List;

public class BookSlugDto extends BookForMainPageDto implements AopDto{

    private String description;

    private List<Author> authorsList;

    public BookSlugDto(Book book, List<Author> authors) {
        super(book);
        this.authorsList = authors;
        this.description = book.getDescription();
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Author> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(List<Author> authorsList) {
        this.authorsList = authorsList;
    }
}
