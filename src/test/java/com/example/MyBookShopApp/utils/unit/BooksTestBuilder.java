package com.example.MyBookShopApp.utils.unit;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.tags.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BooksTestBuilder {

    private final Book book;

    private final TagTestBuilder tagTestBuilder = new TagTestBuilder();

    private final BookReviewTestBuilder bookReviewTestBuilder = new BookReviewTestBuilder();

    public BooksTestBuilder() {
        this.book = new Book();
    }

    public BooksTestBuilder createDescription(){
        book.setDescription("description");
        return this;
    }


    public BooksTestBuilder createAuthor(){
        Author author =new Author();
        author.setName("name");
        author.setDescription("description");
        author.setPhoto("src/test/resources/uploads/820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg");
        book.setAuthors(Collections.singletonList(author));
        return this;
    }
    public BooksTestBuilder createDatePublic(){
        book.setDatePublic(new Date());
        return this;
    }

    public BooksTestBuilder createImage(){
        book.setImage("src/test/resources/uploads/820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg");
        return this;
    }

    public BooksTestBuilder createTitle(){
        book.setTitle("title");
        return this;
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

    public BooksTestBuilder createPrice(){
        book.setPrice(100);
        return this;
    }
    public BooksTestBuilder createIsBestseller(){
        book.setIsBestseller((short) 1);
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
