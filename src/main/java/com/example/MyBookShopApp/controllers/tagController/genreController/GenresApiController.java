package com.example.MyBookShopApp.controllers.tagController.genreController;

import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.service.genresServices.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres/")
public class GenresApiController {

    private final GenreService genreService;

    @Autowired
    public GenresApiController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{slug}")
    public RecommendedBooksDto recommendedBooksDto(
            @PathVariable("slug")String slug,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        return new RecommendedBooksDto(genreService.allBooksByGenreSlug(slug, offset, limit));
    }
}
