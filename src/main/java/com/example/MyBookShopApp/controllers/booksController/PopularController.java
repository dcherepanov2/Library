package com.example.MyBookShopApp.controllers.booksController;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.dto.CartPostponedCounterDto;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.bookServices.Book2UserService;
import com.example.MyBookShopApp.service.bookServices.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class PopularController {

    private final Book2UserService book2UserService;
    private final BookService bookService;

    @Autowired
    public PopularController(Book2UserService book2UserService, BookService bookService) {
        this.book2UserService = book2UserService;
        this.bookService = bookService;
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

    @GetMapping("/popular")
    public String getBooksPopular(Model model,
                                  @CookieValue(name = "cartContents", required = false) String cartContents,
                                  @CookieValue(name = "keptContents", required = false) String keptContents,
                                  JwtUser jwtUser) {
        List<Book2UserEntity> allBookUser = book2UserService.getAllBook2User(jwtUser);
        List<Book> booksPopular = bookService.getPopularBooksData(0, 20);
        if (cartContents != null || keptContents != null || allBookUser != null)
            model.addAttribute("bookMostPopular", new RecommendedBooksDto(booksPopular, keptContents, cartContents, allBookUser));
        else
            model.addAttribute("bookMostPopular", new RecommendedBooksDto(booksPopular));
        return "books/popular";
    }
}
