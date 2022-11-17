package com.example.MyBookShopApp.utils;

import com.example.MyBookShopApp.data.book.review.BookReview;

public class BookReviewTestBuilder {
    private BookReview bookReview;

    public BookReviewTestBuilder() {
        this.bookReview = new BookReview();
    }

    public BookReview build(){
        return bookReview;
    }
}
