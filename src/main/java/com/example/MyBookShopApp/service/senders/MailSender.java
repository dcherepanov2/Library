package com.example.MyBookShopApp.service.senders;

import com.example.MyBookShopApp.dto.ContactRequestDtoV2;
import com.example.MyBookShopApp.service.userServices.helpers.ThreadMailSenderExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MailSender{

    private final ThreadMailSenderExecutor threadMailSenderExecutor;


    @Autowired
    public MailSender(ThreadMailSenderExecutor threadMailSenderExecutor) {
        this.threadMailSenderExecutor = threadMailSenderExecutor;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
                   rollbackFor = {Exception.class, RuntimeException.class})
    public void sendMessage(ContactRequestDtoV2 contact, String code){
        threadMailSenderExecutor.injectContactAndCode(contact,code);
        Thread thread = new Thread(threadMailSenderExecutor);
        thread.start();
    }
}
