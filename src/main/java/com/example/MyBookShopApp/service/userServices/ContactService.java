package com.example.MyBookShopApp.service.userServices;

import com.example.MyBookShopApp.data.enums.ContactType;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.dto.ApproveContactDto;
import com.example.MyBookShopApp.dto.ContactRequestDtoV2;
import com.example.MyBookShopApp.dto.ResponseApproveContact;
import com.example.MyBookShopApp.repo.userrepos.UserContactRepo;
import com.example.MyBookShopApp.service.userServices.helpers.ResponseApproveContactHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactService {
    private final UserContactRepo userContactRepo;
    private final UserServiceImpl userService;

    private final ResponseApproveContactHelper approveContactHelper;

    @Autowired
    public ContactService(UserContactRepo userContactRepo, UserServiceImpl userService, ResponseApproveContactHelper approveContactHelper) {
        this.userContactRepo = userContactRepo;
        this.userService = userService;
        this.approveContactHelper = approveContactHelper;
    }

    public void saveContactDtoEmail(ContactRequestDtoV2 contact, String code) {
        User user = this.getContactEntity(contact);
        if (user == null) {
            user = userService.createNewUserWithUserClientRole(contact.getContact());
        }
        this.createNewContactEntity(contact, code, user);
    }

    public ResponseApproveContact approveContact(ApproveContactDto contact){
        UserContactEntity contactEntity = userContactRepo.findByContact(contact.getContact(), Long.valueOf(contact.getCode().replace(" ", "")));
        Long approve = Long.valueOf(contact.getCode().replace(" ",""));
        if(contactEntity != null && approve.equals(contactEntity.getCode())){
            if(contactEntity.getCodeTrails() >= 10){
                return approveContactHelper.createNumberOfInputAttemptSObject();
            }
            if(contactEntity.getCodeTime().isBefore(LocalDateTime.now().minusDays(1))){
                this.setApproved(contactEntity);
                this.incrementCodeTrails(contactEntity);
                return approveContactHelper.createOldCodeObject();
            }
            return new ResponseApproveContact();
        }
        return approveContactHelper.createIncorrectErrorCodeObject();
    }
    public void createNewContactEntity(ContactRequestDtoV2 contact, String code, User user) {
        UserContactEntity contactEntity = new UserContactEntity();
        contactEntity.setContact(contact.getContact());
        contactEntity.setApproved((short) 0);
        contactEntity.setCode(Long.valueOf(code));
        contactEntity.setType(ContactType.EMAIL);
        contactEntity.setCodeTime(LocalDateTime.now());
        contactEntity.setCodeTrails(0);
        contactEntity.setUserId(user);
        userContactRepo.save(contactEntity);
    }

    public User getContactEntity(ContactRequestDtoV2 contact) {
        String contact1 = contact.getContact();
        return userService.findByEmail(contact1);
    }

    private void setApproved(UserContactEntity contactLocal) {
        contactLocal.setApproved((short) 1);
        userContactRepo.save(contactLocal);
    }

    private void incrementCodeTrails(UserContactEntity contactLocal) {
        contactLocal.setApproved((short) 1);
        contactLocal.setCodeTrails(contactLocal.getCodeTrails() + 1);
        userContactRepo.save(contactLocal);
    }

}
