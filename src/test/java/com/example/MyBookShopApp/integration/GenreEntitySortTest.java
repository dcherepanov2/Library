package com.example.MyBookShopApp.integration;

import com.example.MyBookShopApp.service.genresServices.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreEntitySortTest {
    private final GenreService genreService;

    private List<letscode.javalearn.domain.Department> deps = new ArrayList<>(Arrays.asList(
            new letscode.javalearn.domain.Department(1, 0, "Head"),
            new letscode.javalearn.domain.Department(2, 1, "West"),
            new letscode.javalearn.domain.Department(3, 1, "East"),
            new letscode.javalearn.domain.Department(4, 2, "Germany"),
            new letscode.javalearn.domain.Department(5, 2, "France"),
            new letscode.javalearn.domain.Department(6, 3, "China"),
            new letscode.javalearn.domain.Department(7, 3, "Japan")
    ));

    @Autowired
    public GenreEntitySortTest(GenreService genreService) {

        this.genreService = genreService;
    }

    @Test
    public void test(){
        System.out.println(genreService.allGenreTree());
        //System.out.println(deps.stream().reduce(this::reducer));
    }
}
