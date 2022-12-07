package com.example.MyBookShopApp.utils.unit;

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
