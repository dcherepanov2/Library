package com.example.MyBookShopApp.service.otherServices;

import com.example.MyBookShopApp.data.book.review.MessageEntity;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.ContactMessageDto;
import com.example.MyBookShopApp.repo.otherrepos.ContactsRepo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.userServices.UserService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactsService {
    private final ContactsRepo contactsRepo;
    private final UserServiceImpl userService;

    @Autowired
    public ContactsService(ContactsRepo contactsRepo, UserServiceImpl userService) {
        this.contactsRepo = contactsRepo;
        this.userService = userService;
    }

    public void saveMessage(ContactMessageDto contactMessageDto, JwtUser jwtUser) {
        User user = userService.findByHash(jwtUser.getHash());
        MessageEntity messageEntity = new MessageEntity(contactMessageDto, user);
        contactsRepo.save(messageEntity);
    }
}
