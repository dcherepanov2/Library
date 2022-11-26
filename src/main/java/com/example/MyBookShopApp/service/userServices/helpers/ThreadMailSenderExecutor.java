package com.example.MyBookShopApp.service.userServices.helpers;

import com.example.MyBookShopApp.dto.ContactRequestDtoV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ThreadMailSenderExecutor implements Runnable{

    @Value("${app.mail}")
    private String email;

    private final JavaMailSender javaMailSender;

    private ContactRequestDtoV2 contact;

    private String code;

    public ThreadMailSenderExecutor(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void injectContactAndCode(ContactRequestDtoV2 contact, String code){
        this.contact = contact;
        this.code = code;
    }

    @Override
    public void run() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(email);
        simpleMailMessage.setTo(contact.getContact());
        simpleMailMessage.setSubject("BookShop verification");
        simpleMailMessage.setText("Input this code to verification on site: " + code);
        javaMailSender.send(simpleMailMessage);
    }
}
