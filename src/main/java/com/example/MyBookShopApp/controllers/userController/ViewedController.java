package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.CartPostponedCounterDto;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.dto.UserInfo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import com.example.MyBookShopApp.service.userServices.ViewService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewedController {
    private final ViewService viewService;

    private final UserServiceImpl userService;

    private final BookService bookService;

    public ViewedController(ViewService viewService, UserServiceImpl userService, BookService bookService) {
        this.viewService = viewService;
        this.userService = userService;
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

    @ModelAttribute("userInfo")
    public UserInfo userInfo(@CookieValue("token") String token) {
        return userService.getUserInfoForProfile(token);
    }

    @ModelAttribute("viewedBooks")
    public RecommendedBooksDto viewedBooks(JwtUser jwtUser) {
        User user = userService.findByHash(jwtUser.getHash());
        Page<Book2UserEntity> page = viewService.getPageInViewed(user, 0, 20);
        List<Integer> booksIds = page.getContent().stream().map(Book2UserEntity::getBookId).collect(Collectors.toList());
        List<Book> books = bookService.findAllBooksByIds(booksIds);
        return new RecommendedBooksDto(books);
    }

    @GetMapping("/viewed/books")
    public String getPageViewedBooks() {
        return "/books/viewed";
    }
}
