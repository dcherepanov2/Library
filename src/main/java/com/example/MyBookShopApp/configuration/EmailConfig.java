package com.example.MyBookShopApp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${app.mail}")
    private final String mail = "mailer@vk-isb.ru";

    @Value("${app.mail.password}")
    private final String password = "oYvTAtY&uf25";

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.mail.ru");
        javaMailSender.setPort(465);
        javaMailSender.setUsername(mail);
        javaMailSender.setPassword(password);
        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol","smtps");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.debug","true");
        return javaMailSender;
    }
}
