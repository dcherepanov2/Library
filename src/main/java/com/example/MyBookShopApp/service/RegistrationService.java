package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.enums.ContactType;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.dto.RegistrationForm;
import com.example.MyBookShopApp.repo.BookStoreUserContactEntity;
import com.example.MyBookShopApp.repo.BookStoreUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RegistrationService {

    private final BookStoreUserRepository bookStoreUserRepository;
    private final BookStoreUserContactEntity bookStoreUserContactEntity;

    @Autowired
    public RegistrationService(BookStoreUserRepository bookStoreUserRepository, BookStoreUserContactEntity bookStoreUserContactEntity) {
        this.bookStoreUserRepository = bookStoreUserRepository;
        this.bookStoreUserContactEntity = bookStoreUserContactEntity;
    }

    public void registerNewUser(RegistrationForm registrationForm) {
        if(bookStoreUserRepository.findUserEntityByEmail(registrationForm.getEmail()) == null){
            UserEntity user = new UserEntity();
            UserContactEntity userContactEntity = new UserContactEntity();

            userContactEntity.setType(ContactType.EMAIL);
            userContactEntity.setContact(registrationForm.getEmail());
            userContactEntity.setApproved(Short.parseShort("1"));
            userContactEntity.setCodeTime(LocalDateTime.now());
            int count = userContactEntity.getCodeTrails() + 1;
            userContactEntity.setCodeTrails(count);
            userContactEntity.setCode(UUID.randomUUID().toString().substring(0,5));
            bookStoreUserRepository.save(user);
            userContactEntity.setUserId(user);
            List<UserContactEntity> userContactEntities = user.getContact();
            userContactEntities.add(userContactEntity);
            user.setContact(userContactEntities);
            bookStoreUserRepository.save(user);
            bookStoreUserContactEntity.save(userContactEntity);
        }
    }
}
