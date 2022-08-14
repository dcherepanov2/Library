package com.example.MyBookShopApp.service.senders;

import com.example.MyBookShopApp.data.user.User;
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

    @Value("${domen}")
    private String serverPort;

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    public void sendMessage(User user){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(email);
        simpleMailMessage.setTo(user.getEmail());
        String url = serverPort+"/api/v1/auth/verification/"+user.getUsername()+"/"+ user.getLastCode();
        simpleMailMessage.setSubject("Email verification of repetitor online");
        simpleMailMessage.setText("Go to the link for verification your email on site repetitor online: "
                                    + url);
        javaMailSender.send(simpleMailMessage);
    }
}
