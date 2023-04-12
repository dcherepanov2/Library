package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.dto.*;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.bookServices.Book2UserService;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.service.payment.TransactionService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    private final UserServiceImpl userService;

    private final Book2UserService book2UserService;

    private final TransactionService transactionService;

    private final BookService bookService;

    public ProfileController(UserServiceImpl userService, Book2UserService book2UserService, TransactionService transactionService, BookService bookService) {
        this.userService = userService;
        this.book2UserService = book2UserService;
        this.transactionService = transactionService;
        this.bookService = bookService;
    }

    @GetMapping("/profile")
    public String getProfile(JwtUser jwtUser, Model model) {
        if (jwtUser != null && !jwtUser.getUsername().equals("ANONYMOUS"))
            model.addAttribute("userInfo", userService.getUserInfoForProfile(jwtUser));
        model.addAttribute("contactDto", new ContactRequestDtoV2());
        model.addAttribute("payment", new PaymentRequestDto());
        return "profile";
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

    @GetMapping("/api/transactions")
    @ResponseBody
    public BalanceTransactionPage getAllTransaction(JwtUser jwtUser, Integer limit, Integer offset) {
        List<BalanceTransactionDto> transactions = transactionService.getAllTransactionByUser(jwtUser, offset, limit)
                .stream()
                .map(BalanceTransactionDto::new)
                .collect(Collectors.toList());
        return new BalanceTransactionPage(transactions);
    }

    @ModelAttribute("transactions")
    public BalanceTransactionPage getModelTransaction(JwtUser jwtUser) {
        List<BalanceTransactionDto> transactions = transactionService.getAllTransactionByUser(jwtUser, 0, 6)
                .stream()
                .map(x -> new BalanceTransactionDto(x, bookService.getBookById(x.getBookId())))
                .collect(Collectors.toList());
        return new BalanceTransactionPage(transactions);
    }

    @GetMapping("/my")
    public String getMyPage(@CookieValue(name = "token") String token, Model model, JwtUser jwtUser) {
        List<Book> books = book2UserService.getBooksUser(jwtUser, 1);
        RecommendedBooksDto recommendedBooksDto = new RecommendedBooksDto(books);
        model.addAttribute("books", recommendedBooksDto.getBooks());
        model.addAttribute("userInfo", userService.getUserInfoForProfile(token));
        return "my";
    }

    @GetMapping("/myarchive")
    public String getMyArchivePage(@CookieValue(name = "token") String token, Model model, JwtUser jwtUser) {
        List<Book> books = book2UserService.getBooksUser(jwtUser, 2);
        RecommendedBooksDto recommendedBooksDto = new RecommendedBooksDto(books);
        model.addAttribute("books", recommendedBooksDto.getBooks());
        model.addAttribute("userInfo", userService.getUserInfoForProfile(token));
        return "myarchive";
    }
}
