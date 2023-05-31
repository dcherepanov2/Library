package com.example.MyBookShopApp.controllers.cms;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.dto.BookChangeRequest;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.service.cms.AddAndDeleteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/cms/books")
@Validated
public class ChangesInBooksController {

    private final AddAndDeleteBookService addAndDeleteBookService;

    @Autowired
    public ChangesInBooksController(AddAndDeleteBookService addAndDeleteBookService) {
        this.addAndDeleteBookService = addAndDeleteBookService;
    }

    @PostMapping("/add")
    public void add(@Valid BookChangeRequest request) throws BookException, IOException {
        addAndDeleteBookService.save(request);
    }

    @PostMapping("/delete/{slug}")
    public void deleteBook(@PathVariable("slug") String slug) throws BookException {
        addAndDeleteBookService.deleteBook(slug);
    }

    @PostMapping("/edit/{slug}")
    public void editBook(@Valid BookChangeRequest request, @PathVariable("slug") String slug) throws BookException, IOException {
        addAndDeleteBookService.editBook(request, slug);
    }
}
