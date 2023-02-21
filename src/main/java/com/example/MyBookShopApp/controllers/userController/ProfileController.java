package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.dto.BalanceTransactionDto;
import com.example.MyBookShopApp.dto.ContactRequestDtoV2;
import com.example.MyBookShopApp.dto.PaymentRequestDto;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.service.payment.TransactionService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    private final UserServiceImpl userService;

    private final TransactionService transactionService;

    private final BookService bookService;
    public ProfileController(UserServiceImpl userService, TransactionService transactionService, BookService bookService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.bookService = bookService;
    }

    @GetMapping("/profile")
    public String getProfile(@CookieValue(name = "token") String token, Model model){
        model.addAttribute("userInfo", userService.getUserInfoForProfile(token));
        model.addAttribute("contactDto", new ContactRequestDtoV2());
        model.addAttribute("payment", new PaymentRequestDto());
        return "profile";
    }

    @ModelAttribute("transactions")
    public List<BalanceTransactionDto> getAllTransaction(JwtUser jwtUser){
        return transactionService.getAllTransactionByUser(jwtUser)
                .stream()
                .map(x -> new BalanceTransactionDto(x, bookService.getBookById(x.getBookId())))
                .collect(Collectors.toList());
    }
}
