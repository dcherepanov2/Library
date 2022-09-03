package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.dto.*;
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

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Controller
public class SignInSignUpController {

    private final TwilioService twilioService;
    private final MailSender mailSender;
    private final ValidationService validationService;

    private final UserHelper userHelper;

    private final ContactService contactService;

    @Autowired
    public SignInSignUpController(TwilioService twilioService, MailSender mailSender, ValidationService validationService, UserHelper userHelper, UserServiceImpl userService, ContactService contactService) {
        this.twilioService = twilioService;
        this.mailSender = mailSender;
        this.validationService = validationService;
        this.userHelper = userHelper;
        this.contactService = contactService;
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

//    @RequestMapping(value = "/requestContactConfirmation", method = RequestMethod.POST)
//    public void requestContactConfirmation(ContactRequestDtoV2 contact){
//
//    }

    @PostMapping("/requestContactConfirmation")
    @Async
    public CompletableFuture<ResponseEntity<ResultTrue>> login(@Valid ContactRequestDtoV2 contact
            , BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors())
            validationService.validate(bindingResult);
        else if (validationService.isPhone(contact.getContact())) {
            String code = twilioService.generatedCode(contact.getContact());
            //twilioService.sendSecretCodeSms(code);//
            // TODO: здесь будет логика с смс-рассылкой, на данный момент воюю с тех поддержкой
            ResultTrue resultTrue = new ResultTrue();
            resultTrue.setResult(true);
            return CompletableFuture.completedFuture(ResponseEntity.ok(resultTrue));
        }
        else if (validationService.isEmail(contact.getContact())) {
            String code = userHelper.generateCode();
            mailSender.sendMessage(contact,code);
            contactService.saveContactDtoEmail(contact,code);
            ResultTrue resultTrue = new ResultTrue();
            resultTrue.setResult(true);
            return CompletableFuture.completedFuture(ResponseEntity.ok(resultTrue));
        }//TODO: доработать
        throw new Exception("");
    }

    @PostMapping("/approveContact")
    @Async
    public CompletableFuture<ResponseEntity<ResponseApproveContact>> approveContact(@Valid ApproveContactDto contact
            , BindingResult bindingResult){
        if (bindingResult.hasErrors())
            validationService.validate(bindingResult);
        ResponseApproveContact response =  contactService.approveContact(contact);
        return CompletableFuture.completedFuture(ResponseEntity.ok(response));
    }
}