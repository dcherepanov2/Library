package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.data.user.JwtLogoutToken;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.dto.*;
import com.example.MyBookShopApp.enums.ErrorCodeResponseApproveContact;
import com.example.MyBookShopApp.enums.ErrorMessageResponse;
import com.example.MyBookShopApp.exception.JwtLogoutTokenNotFound;
import com.example.MyBookShopApp.exception.RegistrationException;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
            contactService.saveContactDtoPhone(contact, code);
            //twilioService.sendSecretCodeSms(contact.getContact(), code);// TODO: опять временно не работает из-за проблем с twillio-аккаутном
            ResultTrue resultTrue = new ResultTrue();
            resultTrue.setResult(true);
            return resultTrue;
        } else if (validationService.isEmail(contact.getContact())) {
            String code = userHelper.generateCode();
            mailSender.sendMessage(contact, code);
            contactService.saveContactDtoEmail(contact, code);
            ResultTrue resultTrue = new ResultTrue();
            resultTrue.setResult(true);
            return resultTrue;
        }
        throw new VerificationException(ErrorCodeResponseApproveContact.INCORRECT_ERROR_CODE.getMessage());
    }

    //TODO: переделать под новую реализацию
    @Deprecated
    @SneakyThrows
    @PostMapping("/user/logout")
    public ResultTrue logout(@RequestHeader("Authorization") String token) {
        JwtLogoutToken jwtLogoutToken = userService.logoutToken(token);
        if (jwtLogoutToken != null) {
            ResultTrue resultTrue = new ResultTrue();
            resultTrue.setResult(true);
            return resultTrue;
        }
        throw new JwtLogoutTokenNotFound(ErrorMessageResponse.JWT_TOKEN_IS_NOT_FOUND.getName());
    }

    @PostMapping("/approveContact")
    @SneakyThrows
    public ResponseApproveContact approveContact(@Valid @RequestBody ApproveContactDto contact, HttpServletResponse httpServletResponse, BindingResult bindingResult) {
        UserContactEntity userContact = contactService.findUserContactApprovedByContactName(contact.getContact());
        if (bindingResult.hasErrors())
            validationService.validate(bindingResult);
        else if (userContact != null) {
            User user = userService.findUserByContact(contact.getContact());
            Cookie token = userService.createToken(user);
            httpServletResponse.addCookie(token);
        }
        ResponseApproveContact response = contactService.approveContact(contact);
        if (response == null)
            throw new ResponseApproveContactException(ErrorCodeResponseApproveContact.INCORRECT_ERROR_CODE.getMessage());
        return response;
    }

    @PostMapping("/signup")
    @SneakyThrows
    public User registration(@RequestBody RegistrationForm registrationForm) {
        if (registrationForm != null && contactService.isContactApprove(registrationForm.getEmail()) && contactService.isContactApprove(registrationForm.getPhone())) {
            User user = userService.createNewUserWithUserClientRole(registrationForm);
            UserContactEntity phoneContact = contactService.getContactEntity(registrationForm.getPhone());
            UserContactEntity emailContact = contactService.getContactEntity(registrationForm.getEmail());
            contactService.setUserId(phoneContact, user);
            contactService.setUserId(emailContact, user);
            user.setRoles(null);
            return user;
        }
        throw new RegistrationException("Контакты не были подтверждены.");
    }
}
