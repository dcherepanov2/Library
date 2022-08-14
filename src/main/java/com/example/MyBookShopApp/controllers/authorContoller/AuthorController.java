package com.example.MyBookShopApp.controllers.authorContoller;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.service.authorServices.AuthorService;
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

    @GetMapping("/{slug}")
    public String getAuthor(@PathVariable("slug") String slug, Model model){
        model.addAttribute("findBooksByAuthor",new RecommendedBooksDto(authorService.findBooksByAuthor(slug)));
        Author author = authorService.getBySlug(slug);
        if(author.getDescription().length()>600){
            model.addAttribute("firstDescription", author.getDescription().substring(0,400));
            model.addAttribute("secondDescription", author.getDescription().substring(400));
        }
        else {
            model.addAttribute("firstDescription", author.getDescription());
            model.addAttribute("secondDescription", null);
        }
        model.addAttribute("authorSlug",author);
        return "/authors/slug";
    }
}
