/*package com.example.MyBookShopApp.service.builders;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.data.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class BookBuilderForTest {
    private final Book book;

    private final User user;

    private final SaveObjectUtil saveObjectUtil;


    @Autowired
    public BookBuilderForTest(SaveObjectUtil saveObjectUtil, CreateObjectsUtil createObjectsUtil) {
        this.saveObjectUtil = saveObjectUtil;
        book = CreateObjectsUtil.createBookTest();
        user = CreateObjectsUtil.createUserTest();
    }

    public BookBuilderForTest createTagForBook(){
        Tag tag = CreateObjectsUtil.createTagTest();
        book.setTags(Collections.singletonList(tag));
        //saveObjectUtil.saveTag(tag);
        return this;
    }

    public BookBuilderForTest createBookReview(){
        BookReview bookReview = CreateObjectsUtil.createBookReview(user, book);
        book.setBooksReview(Collections.singletonList(bookReview));
        return this;
    }

    public Book createBook(){
        saveObjectUtil.saveBook(book);
        return book;
    }
}
*/