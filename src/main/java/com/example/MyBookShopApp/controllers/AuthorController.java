package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final Logger logger = Logger.getLogger(AuthorController.class.getName());

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping
    public String getAuthorsPage(Model model){
        List<Author> authors = authorService.getSortAuthor();
        Map<Character, List<Author>> map1 = authors.stream()
                .collect(Collectors.groupingBy(Author::firstLetter));// группировка авторов из бд по алфавиту
        logger.log(Level.INFO,map1.toString());
        model.addAttribute("mapAuthors",map1);
        return "/authors/index";
    }

    @GetMapping("/{id}")
    public String getAuthor(@PathVariable("id") Integer id, Model model){
        Author author = authorService.getById(id);
        model.addAttribute("authorSlug",author);
        return "/authors/slug";
    }

}
