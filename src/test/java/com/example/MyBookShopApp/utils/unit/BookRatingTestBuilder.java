package com.example.MyBookShopApp.utils.unit;

import com.example.MyBookShopApp.data.book.links.BookRating;

public class BookRatingTestBuilder {
    private BookRating bookRating;

    public BookRatingTestBuilder createBookRating(){
        bookRating = new BookRating();
        bookRating.setValue(2);
        return this;
    }

    public BookRatingTestBuilder createBookRatingTestValue(){
        bookRating.setValue(2);
        return this;
    }
    public BookRating build(){
        return bookRating;
    }
}
