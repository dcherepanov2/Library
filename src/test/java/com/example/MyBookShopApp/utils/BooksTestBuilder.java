package com.example.MyBookShopApp.utils;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.tags.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BooksTestBuilder {

    private final Book book;

    private final TagTestBuilder tagTestBuilder = new TagTestBuilder();

    private final BookReviewTestBuilder bookReviewTestBuilder = new BookReviewTestBuilder();

    public BooksTestBuilder() {
        this.book = new Book();

    }

    public BooksTestBuilder createBookReview(){
        List<BookReview> bookReviews = new ArrayList<>();
        for(int i = 0;i<5;i++)
            bookReviews.add(bookReviewTestBuilder.build());
        book.setBooksReview(bookReviews);
        return this;
    }

    public BooksTestBuilder createIdBook(){
        book.setId(1);
        return this;
    }

    public BooksTestBuilder createTagBook(){
        Tag tag = tagTestBuilder.setClick().build();
        book.setTags(Collections.singletonList(tag));
        return this;
    }

    public Book build(){
        return book;
    }

}
