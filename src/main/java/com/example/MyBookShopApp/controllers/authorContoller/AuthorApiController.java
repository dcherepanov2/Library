package com.example.MyBookShopApp.controllers.authorContoller;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.exception.AuthorException;
import com.example.MyBookShopApp.service.authorServices.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
public class AuthorApiController {

    private final AuthorService authorService;

    @Autowired
    public AuthorApiController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/find-author-by-name/{name}")
    public Author getAuthor(@PathVariable("name") String name) throws AuthorException {
        Author byName = authorService.getByName(name);
        if(byName == null)
            throw new AuthorException("Автор с таким именем не найден");
        byName.setBooks(null);
        return byName;
    }
}
