package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.ContactConfirmationPayLoad;
import com.example.MyBookShopApp.dto.ContactConfirmationResponse;
import com.example.MyBookShopApp.dto.RegistrationForm;
import com.example.MyBookShopApp.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthUserController {

    private final RegistrationService registrationService;

    @Autowired
    public AuthUserController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/signin")
    public String handleSignIn(){
        return "signin";
    }


    @GetMapping("/signup")
    public String handleSignUp(Model model){
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestConfirmation(@RequestBody ContactConfirmationPayLoad contactUser){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(true);
        return response;
    }

    @PostMapping("/reg")
    public String handleRegistration(RegistrationForm registrationForm, Model model){
        registrationService.registerNewUser(registrationForm);
        model.addAttribute("regOk", true);
        return "signin";
    }
}
