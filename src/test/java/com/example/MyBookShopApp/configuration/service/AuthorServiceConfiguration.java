package com.example.MyBookShopApp.configuration.service;

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

public class AuthorServiceConfiguration {
    private AuthorService authorService;
    private List<Book> bookArrayList;

    private List<Author> authorList;
    private Book book;

    public void initBookListAndAuthorList(){
        bookArrayList = new ArrayList<>();
        book = new Book();
        List<Author> list = new ArrayList<>();
        for(int i = 0 ;i<25;i++){
            Author author = new Author();
            if(i<10)
                author.setName("Aa");
            else
                author.setName("Bb");
            list.add(author);
            if(i < 20){
                Book book1 = new Book();
                book1.setAuthors(Collections.singletonList(author));
                bookArrayList.add(book1);
            }
        }
        authorList = list;
    }

    public AuthorService initAuthorService(){
        AuthorRepo authorRepo = Mockito.mock(AuthorRepo.class);
        BookRepo bookRepo = Mockito.mock(BookRepo.class);
        if(authorService == null){
            initBookListAndAuthorList();
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
