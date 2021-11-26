package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/postponed")
    public String postponed(){
        return "postponed";
    }

    @GetMapping("/signin")
    public String login(){
        return "signin";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
