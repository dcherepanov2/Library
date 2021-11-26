package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping
    public String getAuthorsPage(Model model){
        Map<Character, List<Author>> mapAuthorToFirstLetter = authorService.getSortAuthor();
        model.addAttribute("mapAuthors", mapAuthorToFirstLetter);
        return "/authors/index";
    }

    @GetMapping("/{id}")
    public String getAuthor(@PathVariable("id") Integer id, Model model){
        Author author = authorService.getById(id);
        model.addAttribute("authorSlug",author);
        return "/authors/slug";
    }
}
