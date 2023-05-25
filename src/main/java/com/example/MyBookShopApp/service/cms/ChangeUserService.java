package com.example.MyBookShopApp.service.cms;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.dto.EditUserInfo;
import com.example.MyBookShopApp.repo.userrepos.UserContactRepo;
import com.example.MyBookShopApp.service.senders.ValidationService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChangeUserService {
    private final UserServiceImpl userService;

    private final UserContactRepo userContactRepo;

    private final ValidationService validationService;

    @Autowired
    public ChangeUserService(UserServiceImpl userService, UserContactRepo userContactRepo, ValidationService validationService) {
        this.userService = userService;
        this.userContactRepo = userContactRepo;
        this.validationService = validationService;
    }

    public void editUserInfo(User userRequest, EditUserInfo userInfo){
        Pageable page = PageRequest.of(0, 1);
        if(userInfo.getPhone() != null && validationService.isPhone(userInfo.getPhone())){
            UserContactEntity phone = userContactRepo.findUserContactEntitiesByUserIdAndCodeTime(userRequest.getId(), (short) 1, page)
                    .getContent().stream().findFirst().orElse(new UserContactEntity());
            if(!phone.getContact().equals(userInfo.getPhone())){
                phone.setContact(userInfo.getPhone());
                userContactRepo.save(phone);
            }
        }
        if(userInfo.getEmail() != null && validationService.isEmail(userInfo.getEmail())){
            UserContactEntity email = userContactRepo.findUserContactEntitiesByUserIdAndCodeTime(userRequest.getId(), (short) 2, page)
                    .getContent().stream().findFirst().orElse(new UserContactEntity());
            if(!email.getContact().equals(userInfo.getPhone())) {
                email.setContact(userInfo.getEmail());
                userContactRepo.save(email);
            }
        }
        if(userInfo.getName() != null){
            userRequest.setUsername(userInfo.getName());
        }
        userService.saveUser(userRequest);
    }
}
