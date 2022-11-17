package com.example.MyBookShopApp.service.senders;

import com.example.MyBookShopApp.dto.ContactRequestDtoV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MailSender {

    @Value("${app.mail}")
    private String email;

    private final JavaMailSender javaMailSender;


    @Autowired
    public MailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
                   rollbackFor = {Exception.class, RuntimeException.class})
    public void sendMessage(ContactRequestDtoV2 contact, String code){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(email);
        simpleMailMessage.setTo(contact.getContact());
        simpleMailMessage.setSubject("BookShop verification");
        simpleMailMessage.setText("Input this code to verification on site: " + code);
        javaMailSender.send(simpleMailMessage);
    }
}
