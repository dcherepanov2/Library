package com.example.MyBookShopApp.controllers.booksController;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.service.bookServices.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;

@Controller
public class BookHeaderController {

    private final BookService bookService;

    @Autowired
    public BookHeaderController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/postponed")
    public String postponed(){
        return "postponed";
    }

    @GetMapping("/cart")
    public String cart(@CookieValue(name = "cartContents", required = false) String cartContents, Model model) {
        List<String> strings = Arrays.asList(cartContents.split("/"));
        List<Book> booksCart = bookService.getBooksBySlugs(strings);
        model.addAttribute("booksCart", booksCart);
        return "cart";
    }
}
