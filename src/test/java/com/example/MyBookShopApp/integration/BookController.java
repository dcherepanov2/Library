package com.example.MyBookShopApp.integration;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.authorrepos.AuthorRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.utils.BooksTestBuilder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
public class BookController {

    private final BookRepo bookRepo;

    private final AuthorRepo authorRepo;

    private final MockMvc mockMvc;
    private Book book;

    @Autowired
    public BookController(BookRepo bookRepo, AuthorRepo authorRepo, MockMvc mockMvc) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void addBook() {
        BooksTestBuilder booksTestBuilder = new BooksTestBuilder();
        book = booksTestBuilder.createIdBook()
                .createDatePublic()
                .createDescription()
                .createTitle()
                .createIsBestseller()
                .createAuthor()
                .createPrice()
                .build();
        authorRepo.saveAll(book.getAuthors());
        bookRepo.save(book);
    }

    @Test
    @SneakyThrows
    public void recentPage() {
        mockMvc.perform(get("/books/recent"))
                .andExpect(content().string(containsString(book.getDescription())))
                .andExpect(content().string(containsString(book.getImage())))
                .andExpect(status().isOk());
    }

    @AfterEach
    public void deleteData() {
        authorRepo.deleteAll();
        bookRepo.deleteAll();
    }
}