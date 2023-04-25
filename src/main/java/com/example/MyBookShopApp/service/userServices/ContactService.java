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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactService {
    private final UserContactRepo userContactRepo;
    private final UserServiceImpl userService;
    @Value("${contact.approve.codetrails}")
    private String codeTrails;
    private final ResponseApproveContactHelper approveContactHelper;

    @Autowired
    public ContactService(UserContactRepo userContactRepo, UserServiceImpl userService, ResponseApproveContactHelper approveContactHelper) {
        this.userContactRepo = userContactRepo;
        this.userService = userService;
        this.approveContactHelper = approveContactHelper;
    }

    public UserContactEntity saveContactDtoEmail(ContactRequestDtoV2 contact, String code) {
        return this.createNewContactEntity(contact, code, ContactType.EMAIL);
    }

    public UserContactEntity saveContactDtoPhone(ContactRequestDtoV2 contact, String code) {
        return this.createNewContactEntity(contact, code, ContactType.PHONE);
    }

    public UserContactEntity setUserId(UserContactEntity userContact, User user) {
        userContact.setUserId(user);
        return userContactRepo.save(userContact);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, RuntimeException.class})
    public ResponseApproveContact approveContact(ApproveContactDto contact) {
        UserContactEntity contactEntity = userContactRepo.findByContact(contact.getContact(), Long.valueOf(contact.getCode().replace(" ", "")));
        Long approve = Long.valueOf(contact.getCode().replace(" ", ""));
        if (contactEntity != null && approve.equals(contactEntity.getCode())) {
            if (contactEntity.getCodeTrails() >= Integer.parseInt(codeTrails)) {
                return approveContactHelper.createNumberOfInputAttemptSObject();
            }
            if (contactEntity.getCodeTime().isBefore(LocalDateTime.now().minusDays(1))) {
                contactEntity.setCodeTrails(contactEntity.getCodeTrails() + 1);
                userContactRepo.save(contactEntity);
                return approveContactHelper.createOldCodeObject();
            }
            contactEntity.setApproved((short) 1);
            contactEntity.setCodeTrails(contactEntity.getCodeTrails() + 1);
            userContactRepo.save(contactEntity);
            return new ResponseApproveContact();
        }
        return approveContactHelper.createIncorrectErrorCodeObject();
    }

    public UserContactEntity createNewContactEntity(ContactRequestDtoV2 contact, String code, ContactType contactType) {
        UserContactEntity contactEntity = new UserContactEntity();
        contactEntity.setContact(contact.getContact());
        contactEntity.setApproved((short) 0);
        contactEntity.setCode(Long.valueOf(code));
        contactEntity.setType(contactType);
        contactEntity.setCodeTime(LocalDateTime.now());
        contactEntity.setCodeTrails(0);
        contactEntity.setUserId(null);
        contactEntity = userContactRepo.save(contactEntity);
        return contactEntity;
    }

    public User getContactEntity(ContactRequestDtoV2 contact) {
        String contact1 = contact.getContact();
        return userService.findUserByContact(contact1);
    }

    public UserContactEntity getContactEntity(String contact){
        return userContactRepo.findByContactOrderByCodeTimeDesc(contact);
    }

    public UserContactEntity findUserContactApprovedByContactName(String contact){
        PageRequest limit = PageRequest.of(0, 1);
        List<UserContactEntity> content = userContactRepo.findUserContactEntitiesApprovedByContactName(contact, limit).getContent();
        if(content.size()> 0)
            return content.get(0);
        return null;
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

    public boolean isContactApprove(String contact) {
        UserContactEntity byContactOrderByCodeTimeDesc = userContactRepo.findByContactOrderByCodeTimeDesc(contact);
        return byContactOrderByCodeTimeDesc != null && byContactOrderByCodeTimeDesc.getApproved() == 1;
    }

    public boolean contactApproveHasUserIdNull(String contact) {
        return userContactRepo.findUserContactEntitiesByContact(contact).stream().noneMatch(x -> x.getUserId() != null);
    }

    public boolean contactApproveHasUserIdNullButApproved(String contact) {
        return userContactRepo.findUserContactEntitiesByContact(contact).stream().anyMatch(x -> x.getApproved() == 1 && x.getUserId() == null);
    }
}
