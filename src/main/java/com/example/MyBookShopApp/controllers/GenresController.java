package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/genres")
public class GenresController {

    private final GenreService genreService;

    @Autowired
    public GenresController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public String getGenresPage(Model model) {
        Map<String, List<Genre>> allGenres = genreService
                .getAllGenres().stream().collect(Collectors.groupingBy(Genre::getType));
        model.addAttribute("allGenres", allGenres);
        return "/genres/index";
    }

    @GetMapping("/{id}")
    public String getGenrePage(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("booksByOneGenre", genreService.getAllBookByOneGenre(id));
        return "/genres/index";
    }

}
