package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.ContactRequestDtoV2;
import com.example.MyBookShopApp.service.userServices.ContactService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignInSignUpController {
    private final UserServiceImpl userService;

    private final ContactService contactService;
    @Autowired
    public SignInSignUpController(UserServiceImpl userService, ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    @PostMapping("/signin")
    public String autorization(ContactRequestDtoV2 contact, Model model, HttpServletResponse response) {
        User user = userService.findUserByContact(contact.getContact());
        if(user != null){
            Cookie token = userService.createToken(user);
            model.addAttribute("userInfo", userService.getUserInfoForProfile(token.getValue()));
            response.addCookie(token);
            return "redirect:/profile";
        }
        else
            throw new UsernameNotFoundException("");
    }

    @GetMapping("/signin")
    public String getSignIn(){
        return "signin";
    }

    @GetMapping("/signup")
    public String getSignUp(){
        return "signup";
    }
}