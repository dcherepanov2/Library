package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.authorrepos.AuthorRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.service.authorServices.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthorServiceTest {
    @MockBean
    private AuthorRepo authorRepo;

    @MockBean
    private BookRepo bookRepo;

    private AuthorService authorService;

    private Author author;
    private ArrayList<Book> list1;

    @BeforeEach
    public void getInitialize(){
        List<Author> list = new ArrayList<>();
        list1 = new ArrayList<>();
        Book book = new Book();
        for(int i = 0 ;i<25;i++){
            Author author = new Author();
            if(i<10)
                author.setName("Aa");
            else
                author.setName("Bb");
            list.add(author);
            if(i < 20){
                Book book1 = new Book();
                list1.add(book1);
            }
        }
        author = list.get(0);
        book.setAuthors(list);
        Pageable pageable = PageRequest.of(0,20);
        Mockito.when(authorRepo.findAll()).thenReturn(list);
        Mockito.when(authorRepo.findBySlug(author.getSlug())).thenReturn(author);
        Mockito.when(bookRepo.findBooksByAuthors(author.getSlug(), pageable)).thenReturn(list1);
        authorService = new AuthorService(authorRepo, bookRepo);
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
        assertEquals(list1, booksByAuthor);
    }
}
