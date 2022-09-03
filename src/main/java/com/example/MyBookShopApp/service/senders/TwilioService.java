package com.example.MyBookShopApp.service.senders;

import com.example.MyBookShopApp.data.enums.ContactType;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.repo.userrepos.UserContactRepo;
import com.example.MyBookShopApp.repo.userrepos.UserRepo;
import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TwilioService {

    private final UserContactRepo userContactRepo;
    private final Random random = new Random();
    private final UserRepo userRepo;

    @Value("${twilio.ACCOUNT_SID}")
    String sid;

    @Value("${twilio.AUTH_TOKEN}")
    String auth;

    @Value("${twilio.NUMBER}")
    String phone;

    @Autowired
    public TwilioService(UserContactRepo userContactRepo, UserRepo userRepo) {
        this.userContactRepo = userContactRepo;
        this.userRepo = userRepo;
    }

    public String generatedCode(String contact) {
        UserContactEntity userContactEntity = new UserContactEntity();
        userContactEntity.setContact(contact);
        Long code = (long) (random.nextInt(899999) + 100000);
        userContactEntity.setCode(code);
        User user = new User();
        user.setId(1L);//TODO: затычка убрать
        userContactEntity.setUserId(user);
        userContactEntity.setApproved((short) 0);
        userContactEntity.setCodeTrails(0);
        userContactEntity.setCodeTime(LocalDateTime.now());
        userContactEntity.setType(ContactType.PHONE);
        userContactRepo.save(userContactEntity);
        return userContactEntity.getCode().toString();
    }

    public void sendSecretCodeSms(String contact) {
        Twilio.init(sid, auth);
        contact = contact.replaceAll("[( )-]", "");
        String code = generatedCode(contact);
        Message.creator(
                new PhoneNumber(contact),
                new PhoneNumber(phone),
                "Your secret key is: " + code
        ).create();
    }
}
