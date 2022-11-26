package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.authorrepos.AuthorRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.service.authorServices.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    private Author author;
    private List<Book> books;

    private AuthorService authorService;
    private List<Author> authors;

    @BeforeEach
    public void init(){
        AuthorRepo authorRepo = Mockito.mock(AuthorRepo.class);
        BookRepo bookRepo = Mockito.mock(BookRepo.class);
            books = new ArrayList<>();
            List<Author> list = new ArrayList<>();
            for(int i = 0 ;i<25;i++) {
                Author author = new Author();
                if (i < 10)
                    author.setName("Aa");
                else
                    author.setName("Bb");
                list.add(author);
                if (i < 20) {
                    Book book1 = new Book();
                    book1.setAuthors(Collections.singletonList(author));
                    books.add(book1);
                }
            }
            author = list.get(0);
            authors = list;
            Pageable pageable = PageRequest.of(0,20);
            Mockito.lenient().when(authorRepo.findAll()).thenReturn(authors);
            Mockito.lenient().when(authorRepo.findBySlug(author.getSlug())).thenReturn(author);
            Mockito.lenient().when(bookRepo.findBooksByAuthors(author.getSlug(), pageable)).thenReturn(books);
            authorService = new AuthorService(authorRepo, bookRepo);
        }

    @Test
    public Map<Character, List<Author>> getSortAuthor(){
        Map<Character, List<Author>> sortAuthor = authorService.getSortAuthor();
        assertEquals(sortAuthor.get("A".charAt(0)).size(), 10);
        assertEquals(sortAuthor.get("B".charAt(0)).size(), 15);
        return sortAuthor;
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
