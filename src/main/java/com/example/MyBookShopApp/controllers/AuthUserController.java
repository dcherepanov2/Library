package com.example.MyBookShopApp.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthUserController {

    @GetMapping("/signin")
    public String handleSignIn(){

        return "signin";
    }
}
