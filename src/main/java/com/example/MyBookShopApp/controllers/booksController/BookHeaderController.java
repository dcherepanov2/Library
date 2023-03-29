package com.example.MyBookShopApp.controllers.booksController;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.service.payment.RobokassaPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class BookHeaderController {

    private final BookService bookService;

    private final RobokassaPaymentService robokassaPaymentService;

    @Autowired
    public BookHeaderController(BookService bookService, RobokassaPaymentService robokassaPaymentService) {
        this.bookService = bookService;
        this.robokassaPaymentService = robokassaPaymentService;
    }

    @GetMapping("/cart")
    public String cart(@CookieValue(name = "cartContents"
            , required = false) String cartContents, Model model) {
        if(cartContents == null || cartContents.equals("")){
            model.addAttribute("booksCart", Collections.EMPTY_LIST);
        }else {
            List<String> strings = Arrays.asList(cartContents.split("/"));
            List<Book> booksCart = bookService.getBooksBySlugs(strings);
            model.addAttribute("booksCart", booksCart);
            Integer integer = booksCart.stream().map(Book::getPrice).reduce(Integer::sum).orElse(0);
            model.addAttribute("allСeptSum", integer.toString());
        }
        return "cart";
    }

    @GetMapping("/postponed")
    public String postponed(@CookieValue(name = "keptContents"
            , required = false) String keptContents, Model model) {
        if(keptContents == null || keptContents.equals(""))
            model.addAttribute("booksKept", Collections.EMPTY_LIST);
        else {
            List<String> strings = Arrays.asList(keptContents.split("/"));
            List<Book> booksCart = bookService.getBooksBySlugs(strings);
            model.addAttribute("booksKept", booksCart);
            Integer integer = booksCart.stream().map(Book::getPrice).reduce(Integer::sum).orElse(0);
            model.addAttribute("allKeptSum", integer.toString());
        }
        return "postponed";
    }
}
