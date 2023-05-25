package com.example.MyBookShopApp.controllers.cms;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.dto.BookChangeRequest;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.service.cms.AddAndDeleteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/cms/books")
public class ChangesInBooksController {

    private final AddAndDeleteBookService addAndDeleteBookService;

    private final BookService bookService;

    @Autowired
    public ChangesInBooksController(AddAndDeleteBookService addAndDeleteBookService, BookService bookService) {
        this.addAndDeleteBookService = addAndDeleteBookService;
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public void add(BookChangeRequest request) throws BookException, IOException {
        addAndDeleteBookService.save(request);
    }

    @PostMapping("/delete/{slug}")
    public void deleteBook(@PathVariable("slug") String slug) throws BookException {
        Book bookBySlug = bookService.getBookBySlug(slug);
        if(bookBySlug== null)
            throw new BookException("Книга не найдена");
        addAndDeleteBookService.deleteBook(bookBySlug);
    }

    @PostMapping("/edit/{slug}")
    public void editBook(BookChangeRequest request,@PathVariable("slug") String slug) throws BookException, IOException {
        Book bookBySlug = bookService.getBookBySlug(slug);
        if(bookBySlug== null)
            throw new BookException("Книга не найдена");
        addAndDeleteBookService.editBook(request,bookBySlug);
    }
}
