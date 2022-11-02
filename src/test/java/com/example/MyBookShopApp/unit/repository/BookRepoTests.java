package com.example.MyBookShopApp.unit.repository;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookRepoTests {

    @MockBean
    private BookRepo bookRepo;

    @Test
    public void findByMostPopular(){
        Book book = new Book();
        book.setId(10000);
        book.setTitle("test");
        book.setDatePublic(new Date());
        book.setImage("");
        book.setPrice(100);
        book.setDiscount((short) 30);
        book.setIsBestseller((short) 1);
        book.setDescription("test");

        bookRepo.save(book);
    }
}
