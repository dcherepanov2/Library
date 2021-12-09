package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.ContactConfirmationPayLoad;
import com.example.MyBookShopApp.dto.ResultTrue;
import com.example.MyBookShopApp.dto.RegistrationForm;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthUserController {

    private final RegistrationService registrationService;
    private final BookService bookService;

    @Autowired
    public AuthUserController(RegistrationService registrationService, BookService bookService) {
        this.registrationService = registrationService;
        this.bookService = bookService;
    }

    @GetMapping("/signin")
    public String handleSignIn() {
        return "signin";
    }


    @GetMapping("/signup")
    public String handleSignUp(Model model) {
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ResultTrue handleRequestConfirmation(@RequestBody ContactConfirmationPayLoad contactUser) {
        ResultTrue response = new ResultTrue();
        response.setResult(true);
        return response;
    }

    @PostMapping("/reg")
    public String handleRegistration(RegistrationForm registrationForm, Model model) {
        registrationService.registerNewUser(registrationForm);
        model.addAttribute("regOk", true);
        return "signin";
    }

//    @PostMapping("/rateBook/{slug}")
//    public String changeRateBook(@PathVariable("slug") String slug, @RequestParam("value")Byte rate) {
//        Byte rate = bookService.changeRateBookBySlug(slug, rate);
//        return "/books/" + slug;
//    }
}
