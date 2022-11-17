package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.configuration.EmailConfig;
import com.example.MyBookShopApp.dto.ContactRequestDtoV2;
import com.example.MyBookShopApp.service.senders.MailSender;
import io.swagger.v3.oas.annotations.info.Contact;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.subethamail.wiser.Wiser;

import static com.example.MyBookShopApp.utils.WiserAssertions.assertReceivedMessage;

// TODO: не работает,вернусь к нему позже,т.к. не модульный тест,
// TODO: мы тестируем работу библиотеки отправки сообщения на почту
@SpringBootTest
public class MailSenderTest {

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private MailSender mailSender;
    private Wiser wiser;

    @BeforeEach
    public void init(){
        wiser = new Wiser();
        wiser.start();
    }

    @Test
    public void senderMailMessage(){
        ContactRequestDtoV2 contactRequestDtoV2 = new ContactRequestDtoV2();
        contactRequestDtoV2.setContact("danilcherep7@gmail.com");
        mailSender.sendMessage(contactRequestDtoV2,"123 123");
        assertReceivedMessage(wiser)
                .from(emailConfig.getMail())
                .to(emailConfig.getMail())
                .withSubject("BookShop verification")
                .withContent("Input this code to verification on site: ");
    }

    @AfterEach
    public void wiserStop(){
        wiser.stop();
    }
}
