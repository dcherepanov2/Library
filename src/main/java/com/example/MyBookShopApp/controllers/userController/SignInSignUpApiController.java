package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.data.user.JwtLogoutToken;
import com.example.MyBookShopApp.dto.ApproveContactDto;
import com.example.MyBookShopApp.dto.ContactRequestDtoV2;
import com.example.MyBookShopApp.dto.ResponseApproveContact;
import com.example.MyBookShopApp.dto.ResultTrue;
import com.example.MyBookShopApp.enums.ErrorCodeResponseApproveContact;
import com.example.MyBookShopApp.enums.ErrorMessageResponse;
import com.example.MyBookShopApp.exception.JwtLogoutTokenNotFound;
import com.example.MyBookShopApp.exception.ResponseApproveContactException;
import com.example.MyBookShopApp.exception.VerificationException;
import com.example.MyBookShopApp.service.senders.MailSender;
import com.example.MyBookShopApp.service.senders.TwilioService;
import com.example.MyBookShopApp.service.senders.ValidationService;
import com.example.MyBookShopApp.service.userServices.ContactService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import com.example.MyBookShopApp.service.userServices.helpers.UserHelper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SignInSignUpApiController {

    private final TwilioService twilioService;
    private final MailSender mailSender;
    private final ValidationService validationService;

    private final UserHelper userHelper;

    private final ContactService contactService;

    private final UserServiceImpl userService;

    @Autowired
    public SignInSignUpApiController(TwilioService twilioService, MailSender mailSender, ValidationService validationService, UserHelper userHelper, UserServiceImpl userService, ContactService contactService) {
        this.twilioService = twilioService;
        this.mailSender = mailSender;
        this.validationService = validationService;
        this.userHelper = userHelper;
        this.contactService = contactService;
        this.userService = userService;
    }

    @PostMapping("/requestContactConfirmation")
    public ResultTrue login(@Valid @RequestBody ContactRequestDtoV2 contact
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
            return resultTrue;
        }//TODO: опять временно не работает из-за проблем с twillio-аккаутном
        else if (validationService.isEmail(contact.getContact())) {
            String code = userHelper.generateCode();
            userService.createNewUserWithUserClientRole(contact.getContact());
            mailSender.sendMessage(contact,code);
            contactService.saveContactDtoEmail(contact,code);
            ResultTrue resultTrue = new ResultTrue();
            resultTrue.setResult(true);
            return resultTrue;
        }
        throw new VerificationException(ErrorCodeResponseApproveContact.INCORRECT_ERROR_CODE.getMessage());
    }

    @SneakyThrows
    @PostMapping("/user/logout")
    public ResultTrue logout(@RequestHeader("Authorization") String token){
        JwtLogoutToken jwtLogoutToken = userService.logoutToken(token);
        if(jwtLogoutToken != null){
            ResultTrue resultTrue = new ResultTrue();
            resultTrue.setResult(true);
            return resultTrue;
        }
        throw new JwtLogoutTokenNotFound(ErrorMessageResponse.JWT_TOKEN_IS_NOT_FOUND.getName());
    }

    @PostMapping("/approveContact")
    @SneakyThrows
    public ResponseApproveContact approveContact(@Valid @RequestBody ApproveContactDto contact, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            validationService.validate(bindingResult);
        ResponseApproveContact response =  contactService.approveContact(contact);
        if(response == null)
            throw new ResponseApproveContactException(ErrorCodeResponseApproveContact.INCORRECT_ERROR_CODE.getMessage());
        return response;
    }
}
