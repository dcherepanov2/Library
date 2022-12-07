package com.example.MyBookShopApp.service.senders;

import com.example.MyBookShopApp.data.enums.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;


//TODO: пока не работает, так как надо задеплоить его на сервер Linux, чтобы завести домен яндекс для корректной работы
@Service
@Deprecated
public class MailService {

    /*private final JavaMailSender javaMailSender;
    private final Random random = new Random();
    private final UserContactRepo userContactRepo;

    @Autowired
    public MailService(JavaMailSender javaMailSender, UserContactRepo userContactRepo) {
        this.javaMailSender = javaMailSender;
        this.userContactRepo = userContactRepo;
    }

    public String generatedCode(String contact){
        int code = random.nextInt(899999)+100000;
        UserContactEntity userContactEntity = new UserContactEntity();
        userContactEntity.setCode((long) code);
        userContactEntity.setCodeTrails(0);
        userContactEntity.setApproved((short) 0);
        userContactEntity.setCodeTime(LocalDateTime.now());
        userContactEntity.setContact(contact);
        userContactEntity.setType(ContactType.EMAIL);
        User user = new User();
        user.setId(1);//TODO: доработать
        userContactEntity.setUserId(user);
        userContactRepo.save(userContactEntity);
        return String.valueOf(code);
    }

    public void sendMessage(String emailTo, String subject, String message){
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("");
        emailMessage.setTo(emailTo);
        emailMessage.setSubject(subject);
        emailMessage.setText("Hello! Welcome to BookShop. Your activation code: " + message);
        javaMailSender.send(emailMessage);
    }*/
}
