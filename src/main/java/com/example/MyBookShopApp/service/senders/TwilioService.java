package com.example.MyBookShopApp.service.senders;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.repo.userrepos.UserContactRepo;
import com.example.MyBookShopApp.service.userServices.ContactService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TwilioService {

    private final UserContactRepo userContactRepo;
    private final Random random = new Random();

    private final UserServiceImpl userService;

    private final ContactService contactService;



    @Value("${twilio.ACCOUNT_SID}")
    String sid;

    @Value("${twilio.AUTH_TOKEN}")
    String auth;

    @Value("${twilio.NUMBER}")
    String phone;

    @Autowired
    public TwilioService(UserContactRepo userContactRepo, UserServiceImpl userService, ContactService contactService) {
        this.userContactRepo = userContactRepo;
        this.userService = userService;
        this.contactService = contactService;
    }

    private UserContactEntity generatedCode(String contact) {
        UserContactEntity userContactEntity = new UserContactEntity();
        userContactEntity.setContact(contact);
        Long code = (long) (random.nextInt(899999) + 100000);
        userContactEntity.setCode(code);
        return userContactEntity;
    }

    public void sendSecretCodeSms(String contact, String code) {
        Twilio.init(sid, auth);
        contact = contact.replaceAll("[( )-]", "");
        Message.creator(
                new PhoneNumber(contact),
                new PhoneNumber(phone),
                "Your secret key is: " + code
        ).create();
    }
}