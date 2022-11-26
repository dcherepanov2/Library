package com.example.MyBookShopApp.configuration;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestObject {

    private final Book book = new Book();
    private final List<Book> bookArrayList = new ArrayList<>();

    private final List<Author> authorArrayList = new ArrayList<>();

    private final Author author;

    private final Pageable pageable = PageRequest.of(0,20);

    public TestObject() {
        initArrayBook();
        author = authorArrayList.get(0);
    }

    private void initArrayBook(){
        List<Author> list = new ArrayList<>();
        for(int i = 0 ;i<25;i++){
            Author author = new Author();
            if(i<10)
                author.setName("Aa");
            else
                author.setName("Bb");
            authorArrayList.add(author);
            if(i < 20){
                Book book1 = new Book();
                book1.setAuthors(Collections.singletonList(author));
                bookArrayList.add(book1);
            }
        }
    }

    public Object getObject(String fieldName){
        Object obj;
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            obj = field.get(this);
            return obj;
        }catch (NoSuchFieldException | IllegalAccessException e){
            throw new AssertionError(e.getMessage());
        }
    }
}
