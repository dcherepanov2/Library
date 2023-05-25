package com.example.MyBookShopApp.controllers.tagController.genreController;

import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.dto.CartPostponedCounterDto;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.service.genresServices.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/genres")
public class GenresController {

    private final GenreService genreService;

    @Autowired
    public GenresController(GenreService genreService) {
        this.genreService = genreService;
    }

    @ModelAttribute("countPostponed")
    public CartPostponedCounterDto cartPostponedCounterDto(@CookieValue(name = "cartContents", required = false) String cartContents,
                                                           @CookieValue(name = "keptContents", required = false) String keptContents) {
        CartPostponedCounterDto cartPostponedCounterDto = new CartPostponedCounterDto();
        if (cartContents != null && !cartContents.equals(""))
            cartPostponedCounterDto.setCountCart(cartContents.split("/").length);
        if (keptContents != null && !keptContents.equals(""))
            cartPostponedCounterDto.setCountPostponed(keptContents.split("/").length);
        return cartPostponedCounterDto;
    }

    @GetMapping
    public String getGenresPage(Model model) {
        model.addAttribute("genres", genreService.allGenreTree());
        return "/genres/index";
    }

    @GetMapping("/{slug}")
    public String getGenrePage(@PathVariable("slug") String slug, Model model) {
        RecommendedBooksDto recommendedBooksDto = new RecommendedBooksDto(genreService.allBooksByGenreSlug(slug, 0, 20));
        GenreEntity genreEntity = genreService.findBySlug(slug);
        model.addAttribute("booksByOneGenre",recommendedBooksDto);
        model.addAttribute("genreName",genreEntity.getName());
        return "/genres/slug";
    }
}
