package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.dto.RegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInSignUpController {

    @GetMapping("/signin")
    public String getSignIn() {
        return "signin";
    }

    @GetMapping("/signup")
    public String getSignUp(Model model) {
        model.addAttribute("regForm",new RegistrationForm());
        return "signup";
    }
}
