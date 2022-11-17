package com.example.MyBookShopApp.utils;

import com.example.MyBookShopApp.data.book.Book;

import java.util.ArrayList;
import java.util.List;

public class CreateObjectForTesting {
    private static List<Book> books;
    public static List<Book> createBookList(int count){
        if(books == null)
            books = new ArrayList<>();
        else if (count == books.size())
            return books;
        for(int i = 0;i<count;i++){
            Book book = new Book();
            books.add(book);
        }
        return books;
    }
}
