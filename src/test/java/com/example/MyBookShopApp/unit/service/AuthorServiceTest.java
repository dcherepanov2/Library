package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.service.authorServices.AuthorService;
import com.example.MyBookShopApp.configuration.MockitoApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Spy
    private MockitoApplicationContext context;

    private Author author;
    private List<Book> books;

    private AuthorService authorService;
    @BeforeEach
    public void init(){
        authorService = (AuthorService) context.getBean(AuthorService.class);
        books = (List<Book>) context.getFieldTestClassByName("bookArrayList", AuthorService.class);
        author = books.get(0).getAuthors().get(0);
    }

    @Test
    public void getSortAuthor(){
        Map<Character, List<Author>> sortAuthor = authorService.getSortAuthor();
        assertEquals(sortAuthor.get("A".charAt(0)).size(), 10);
        assertEquals(sortAuthor.get("B".charAt(0)).size(), 15);
    }

    @Test
    public void getBySlug(){
        Author authorLocal = authorService.getBySlug(author.getSlug());
        assertEquals(authorLocal, author);
    }

    @Test
    public void findBooksByAuthor(){
        List<Book> booksByAuthor = authorService.findBooksByAuthor(author.getSlug());
        assertEquals(booksByAuthor.size(), 20);
        assertEquals(books, booksByAuthor);
    }
}
