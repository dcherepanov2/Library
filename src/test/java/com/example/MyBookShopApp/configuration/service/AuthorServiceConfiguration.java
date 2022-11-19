package com.example.MyBookShopApp.configuration.service;

import com.example.MyBookShopApp.configuration.MockitoRepositoryConfiguration;
import com.example.MyBookShopApp.configuration.TestObject;
import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.authorrepos.AuthorRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.service.authorServices.AuthorService;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuthorServiceConfiguration {//комменатрий
    private AuthorService authorService;
    private List<Book> bookArrayList;

    private List<Author> authorList;
    private Book book;

    private MockitoRepositoryConfiguration mockitoRepositoryConfiguration;

    private TestObject testObject = new TestObject();


    public AuthorService initAuthorService(){
        mockitoRepositoryConfiguration = new MockitoRepositoryConfiguration();
        AuthorRepo authorRepo = mockitoRepositoryConfiguration.getAuthorRepo();
        BookRepo bookRepo = mockitoRepositoryConfiguration.getBookRepo();
        authorList = (List<Author>) testObject.getObject("authorList");
        bookArrayList = (List<Book>) testObject.getObject("bookArrayList");
        book = (Book) testObject.getObject("book");
        if(authorService == null){
            Author author = authorList.get(0);
            book.setAuthors(authorList);
            Pageable pageable = PageRequest.of(0,20);
            Mockito.lenient().when(authorRepo.findAll()).thenReturn(authorList);
            Mockito.lenient().when(authorRepo.findBySlug(author.getSlug())).thenReturn(author);
            Mockito.lenient().when(bookRepo.findBooksByAuthors(author.getSlug(), pageable)).thenReturn(bookArrayList);
        }
        return authorService = new AuthorService(authorRepo, bookRepo);
    }
}
