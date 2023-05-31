package com.example.MyBookShopApp.controllers.cms;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.dto.AuthorEditDto;
import com.example.MyBookShopApp.exception.AuthorException;
import com.example.MyBookShopApp.service.authorServices.AuthorService;
import com.example.MyBookShopApp.service.cms.ChangeInAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cms/authors")
@Validated
public class ChangeInAuthorController {

    private final ChangeInAuthorService changeInAuthorService;

    private final AuthorService authorService;

    @Autowired
    public ChangeInAuthorController(ChangeInAuthorService changeInAuthorService, AuthorService authorService) {
        this.changeInAuthorService = changeInAuthorService;
        this.authorService = authorService;
    }

    @PostMapping("/edit/{slug}")
    public String editAuthor(@RequestBody @Valid AuthorEditDto request, @PathVariable("slug") String slug) throws AuthorException {
        Author author = authorService.getBySlug(slug);
        if (author == null)
            throw new AuthorException("Автор с указанным индетификатором не найден");
        changeInAuthorService.editAuthor(request, author);
        return "index";
    }
}
