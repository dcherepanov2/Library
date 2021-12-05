package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.service.GenreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres/")
public class GenresApiController {

    private GenreService genreService;

    @GetMapping("/{slug}")
    public RecommendedBooksDto recommendedBooksDto(
            @PathVariable("slug")String slug,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        return new RecommendedBooksDto(genreService.allBooksByGenre(slug, offset,limit));
    }
}
