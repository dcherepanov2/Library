package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.data.user.JwtLogoutToken;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.*;
import com.example.MyBookShopApp.enums.ErrorCodeResponseApproveContact;
import com.example.MyBookShopApp.exception.VerificationException;
import com.example.MyBookShopApp.service.senders.MailSender;
import com.example.MyBookShopApp.service.senders.TwilioService;
import com.example.MyBookShopApp.service.senders.ValidationService;
import com.example.MyBookShopApp.service.userServices.ContactService;
import com.example.MyBookShopApp.service.userServices.helpers.UserHelper;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Controller
public class SignInSignUpController {

    private final TwilioService twilioService;
    private final MailSender mailSender;
    private final ValidationService validationService;

    private final UserHelper userHelper;

    private final ContactService contactService;
    
    private final UserServiceImpl userService;

    @Autowired
    public SignInSignUpController(TwilioService twilioService, MailSender mailSender, ValidationService validationService, UserHelper userHelper, UserServiceImpl userService, ContactService contactService) {
        this.twilioService = twilioService;
        this.mailSender = mailSender;
        this.validationService = validationService;
        this.userHelper = userHelper;
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

    @PostMapping("/requestContactConfirmation")
    @Async
    public CompletableFuture<ResponseEntity<ResultTrue>> login(@Valid @RequestBody ContactRequestDtoV2 contact
            , BindingResult bindingResult) throws VerificationException {
        if (bindingResult.hasErrors())
            validationService.validate(bindingResult);
        else if (validationService.isPhone(contact.getContact())) {
            String code = userHelper.generateCode();
            userService.createNewUserWithUserClientRole(contact.getContact());
            contactService.saveContactDtoPhone(contact,code);
            twilioService.sendSecretCodeSms(contact.getContact(), code);
            ResultTrue resultTrue = new ResultTrue();
            resultTrue.setResult(true);
            return CompletableFuture.completedFuture(ResponseEntity.ok(resultTrue));
        }
        else if (validationService.isEmail(contact.getContact())) {
            String code = userHelper.generateCode();
            userService.createNewUserWithUserClientRole(contact.getContact());
            mailSender.sendMessage(contact,code);
            contactService.saveContactDtoEmail(contact,code);
            ResultTrue resultTrue = new ResultTrue();
            resultTrue.setResult(true);
            return CompletableFuture.completedFuture(ResponseEntity.ok(resultTrue));
        }
        throw new VerificationException(ErrorCodeResponseApproveContact.INCORRECT_ERROR_CODE.getMessage());
    }

    @PostMapping("/approveContact")
    @Async
    public CompletableFuture<ResponseEntity<ResponseApproveContact>> approveContact(@Valid @RequestBody ApproveContactDto contact
            , BindingResult bindingResult){
        if (bindingResult.hasErrors())
            validationService.validate(bindingResult);
        ResponseApproveContact response =  contactService.approveContact(contact);
        return CompletableFuture.completedFuture(ResponseEntity.ok(response));
    }

    @PostMapping("/logout")
    public JwtLogoutToken logout(@RequestHeader("Bearer_token")String token){
        JwtLogoutToken jwtLogoutToken = userService.logoutToken(token);
        //TODO: доработать исключение
        return jwtLogoutToken;
    }
}