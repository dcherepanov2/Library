package com.example.MyBookShopApp.controllers.booksController;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.dto.CartPostponedCounterDto;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.bookServices.Book2UserService;
import com.example.MyBookShopApp.service.bookServices.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class BookHeaderController {

    private final BookService bookService;

    private final Book2UserService book2UserService;

    private static final String MODEL_KEY_NAME_BOOK_CART = "bookCart";

    private static final String MODEL_KEY_NAME_BOOK_KEPT = "bookKept";

    @Autowired
    public BookHeaderController(BookService bookService, Book2UserService book2UserService) {
        this.bookService = bookService;
        this.book2UserService = book2UserService;
    }

    @ModelAttribute("countPostponed")
    public CartPostponedCounterDto cartPostponedCounterDto(@CookieValue(name = "cartContents", required = false) String cartContents,
                                                           @CookieValue(name = "keptContents", required = false) String keptContents) {
        CartPostponedCounterDto cartPostponedCounterDto = new CartPostponedCounterDto();
        if (cartContents != null && !cartContents.equals(""))
            cartPostponedCounterDto.setCountCart(cartContents.split("/").length);
        if (keptContents != null && !keptContents.equals(""))
            cartPostponedCounterDto.setCountPostponed(keptContents.split("/").length);
        return cartPostponedCounterDto;
    }

    @GetMapping("/cart")
    public String cart(@CookieValue(name = "cartContents", required = false) String cartContents, Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute(MODEL_KEY_NAME_BOOK_CART,  Collections.emptyList());
        } else {
            List<String> strings = Arrays.asList(cartContents.split("/"));
            List<Book> booksCart = bookService.getBooksBySlugs(strings);
            model.addAttribute(MODEL_KEY_NAME_BOOK_CART, booksCart);
            Integer integer = booksCart.stream().map(Book::getPrice).reduce(Integer::sum).orElse(0);
            model.addAttribute("allСeptSum", integer.toString());
        }
        return "cart";
    }

    @GetMapping("/cart/v2")
    public String cartV2(JwtUser jwtUser, Model model) {
        if (jwtUser != null && !jwtUser.getUsername().equals("ANONYMOUS")) {
            List<Book> booksCart = book2UserService.getBooksUser(jwtUser, 3);
            model.addAttribute(MODEL_KEY_NAME_BOOK_CART, booksCart);
            Integer integer = booksCart.stream().map(Book::getPrice).reduce(Integer::sum).orElse(0);
            model.addAttribute("allСeptSum", integer.toString());
        }
        return "cart";
    }

    @GetMapping("/postponed")
    public String postponed(@CookieValue(name = "keptContents"
            , required = false) String keptContents, Model model) {
        if (keptContents == null || keptContents.equals(""))
            model.addAttribute("booksKept", Collections.emptyList());
        else {
            List<String> strings = Arrays.asList(keptContents.split("/"));
            List<Book> booksCart = bookService.getBooksBySlugs(strings);
            model.addAttribute(MODEL_KEY_NAME_BOOK_KEPT, booksCart);
            Integer integer = booksCart.stream().map(Book::getPrice).reduce(Integer::sum).orElse(0);
            model.addAttribute("allKeptSum", integer.toString());
        }
        return "postponed";
    }

    @GetMapping("/postponed/v2")
    public String postponedV2(JwtUser jwtUser, Model model) {
        List<Book> booksUser = book2UserService.getBooksUser(jwtUser, 4);
        model.addAttribute(MODEL_KEY_NAME_BOOK_KEPT, booksUser);
        return "postponed";
    }
}
