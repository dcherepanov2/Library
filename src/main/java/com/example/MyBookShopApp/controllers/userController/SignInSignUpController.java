package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.dto.ApproveContactDto;
import com.example.MyBookShopApp.dto.RegistrationForm;
import com.example.MyBookShopApp.dto.ResponseApproveContact;
import com.example.MyBookShopApp.enums.ErrorCodeResponseApproveContact;
import com.example.MyBookShopApp.exception.ResponseApproveContactException;
import com.example.MyBookShopApp.service.senders.ValidationService;
import com.example.MyBookShopApp.service.userServices.ContactService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class SignInSignUpController {

    private final ValidationService validationService;


    private final ContactService contactService;
    
    private final UserServiceImpl userService;

    @Autowired
    public SignInSignUpController(ValidationService validationService,  UserServiceImpl userService, ContactService contactService) {
        this.validationService = validationService;
        this.contactService = contactService;
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String getSignIn() {
        return "signin";
    }

    @GetMapping("/signup")
    public String getSignUp(Model model) {
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }


}