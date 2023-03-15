package com.example.MyBookShopApp.integration;

import com.example.MyBookShopApp.service.genresServices.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@AutoConfigureMockMvc
public class GenreEntitySortTest {
    private final GenreService genreService;


    @Autowired
    public GenreEntitySortTest(GenreService genreService) {

        this.genreService = genreService;
    }

    @Test
    public void test(){
        genreService.allBooksByGenreSlug("genre-IGn-343", 0, 20).forEach(System.out::println);
    }
}
