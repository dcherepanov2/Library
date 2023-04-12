package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.dto.*;
import com.example.MyBookShopApp.enums.ErrorCodeResponseApproveContact;
import com.example.MyBookShopApp.exception.RegistrationException;
import com.example.MyBookShopApp.exception.ResponseApproveContactException;
import com.example.MyBookShopApp.exception.VerificationException;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.senders.MailSender;
import com.example.MyBookShopApp.service.senders.TwilioService;
import com.example.MyBookShopApp.service.senders.ValidationService;
import com.example.MyBookShopApp.service.userServices.ContactService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import com.example.MyBookShopApp.service.userServices.helpers.UserHelper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Controller
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

    @ModelAttribute("countPostponed")
    public CartPostponedCounterDto cartPostponedCounterDto(@CookieValue(name = "cartContents", required = false) String cartContents,
                                                           @CookieValue(name = "keptContents", required = false) String keptContents) {
        CartPostponedCounterDto cartPostponedCounterDto = new CartPostponedCounterDto();
        if (cartContents != null && !cartContents.equals(""))
            cartPostponedCounterDto.setCountCart(cartContents.split("/").length);
        if (keptContents != null && !keptContents.equals(""))
            cartPostponedCounterDto.setCountPostponed(keptContents.split("/").length);
        return cartPostponedCounterDto;
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
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

    @SneakyThrows
    @GetMapping("/user/logout")
    public void logout(@CookieValue(value = "token", required = false) String token, HttpServletResponse httpServletResponse) {
        userService.logoutToken(token);
        httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL("/"));
    }

    @PostMapping("/approveContact")
    @SneakyThrows
    @ResponseBody
    public ResultTrue approveContact(@Valid @RequestBody ApproveContactDto contact,
                                     HttpServletResponse httpServletResponse,
                                     BindingResult bindingResult) {
        UserContactEntity userContact = contactService.findUserContactApprovedByContactName(contact.getContact());
        if (bindingResult.hasErrors())
            validationService.validate(bindingResult);
        ResponseApproveContact response = contactService.approveContact(contact);
        if (response == null)
            throw new ResponseApproveContactException(ErrorCodeResponseApproveContact.INCORRECT_ERROR_CODE.getMessage());
        if (userContact != null && contactService.contactApproveHasUserIdNullButApproved(contact.getContact())) {
            User user = userService.findUserByContact(contact.getContact());
            Cookie token = userService.createToken(user);
            httpServletResponse.addCookie(token);
            response.setError("Авторизация прошла успешно. Обновите страницу, чтобы перейти в личный кабинет сайта.");
            return response;
        }
        if (userContact != null && !contactService.contactApproveHasUserIdNull(contact.getContact())) {
            response.setError("Пользователь с этим контактом уже зарегистрирован в системе.");
            return response;
        }
        if (userContact != null) {
            return response;
        } else if (response.getReturnResponse().equals(true)) {
            contactService.approveContact(contact);
            response.setError("Контакт успешно подтвержден.");
            return response;
        }
        throw new ResponseApproveContactException("Указанный контакт не найден.");
    }

    @GetMapping("/approveContactToJson")
    @ResponseBody
    @SneakyThrows
    public ResponseApproveContact responseApproveContact(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            String redirect = (String) inputFlashMap.get("redirect");
            if (redirect != null) {
                httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL("/" + redirect));
            }
        }
        if (inputFlashMap != null) {
            return (ResponseApproveContact) inputFlashMap.get("response");
        }
        throw new ResponseApproveContactException("Не был передан объект класса ResponseApproveContact.");
    }

    @PostMapping("/signup")
    @SneakyThrows
    @ResponseBody
    public User registration(@RequestBody RegistrationForm registrationForm) {
        if (registrationForm != null && contactService.isContactApprove(registrationForm.getEmail()) && contactService.isContactApprove(registrationForm.getPhone())) {
            if (contactService.contactApproveHasUserIdNull(registrationForm.getEmail()) && contactService.contactApproveHasUserIdNull(registrationForm.getPhone())) {
                User user = userService.createNewUserWithUserClientRole(registrationForm);
                UserContactEntity phoneContact = contactService.getContactEntity(registrationForm.getPhone());
                UserContactEntity emailContact = contactService.getContactEntity(registrationForm.getEmail());
                contactService.setUserId(phoneContact, user);
                contactService.setUserId(emailContact, user);
                user.setRoles(null);
                return user;
            }
            throw new RegistrationException("Эти контакты уже зарегистрораванны в системе. Измените их в форме регистрации.");
        }
        throw new RegistrationException("Контакты не были подтверждены.");
    }
}
