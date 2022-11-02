package com.example.MyBookShopApp.unit.repository;

import com.example.MyBookShopApp.repo.bookrepos.BookFilesRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BookFilesRepoTest {
    @Autowired
    private BookFilesRepo bookFilesRepo;

    @Test
    public void injectRepo(){
        assertNotNull(bookFilesRepo);
    }

    @Test
    public void saveObject(){

    }
}
