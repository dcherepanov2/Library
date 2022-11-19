package com.example.MyBookShopApp.configuration;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.authorrepos.AuthorRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class MockitoRepositoryConfiguration {

    private final TestObject testObject = new TestObject();

    public AuthorRepo getAuthorRepo(){
        List<Author> authorList = (List<Author>) testObject.getObject("authorArrayList");
        Author author = (Author) testObject.getObject("author");
        AuthorRepo authorRepo = Mockito.mock(AuthorRepo.class);
        Mockito.lenient().when(authorRepo.findAll()).thenReturn(authorList);
        Mockito.lenient().when(authorRepo.findBySlug(author.getSlug())).thenReturn(author);
        return authorRepo;
    }

    public BookRepo getBookRepo(){
        BookRepo bookRepo = Mockito.mock(BookRepo.class);
        Author author = (Author) testObject.getObject("author");
        Pageable pageable = (Pageable) testObject.getObject("pageable");
        List<Book> bookArrayList = (List<Book>) testObject.getObject("bookArrayList");
        Mockito.lenient().when(bookRepo.findBooksByAuthors(author.getSlug(), pageable)).thenReturn(bookArrayList);
        return bookRepo;
    }
}
