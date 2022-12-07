package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.dto.ApproveContactDto;
import com.example.MyBookShopApp.dto.ContactRequestDtoV2;
import com.example.MyBookShopApp.dto.ResponseApproveContact;
import com.example.MyBookShopApp.repo.userrepos.UserContactRepo;
import com.example.MyBookShopApp.service.userServices.ContactService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import com.example.MyBookShopApp.service.userServices.helpers.ResponseApproveContactHelper;
import com.example.MyBookShopApp.utils.unit.ApproveContactTestBuilder;
import com.example.MyBookShopApp.utils.unit.UserTestBuilder;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ContactServiceTest {

    @MockBean
    private UserContactRepo userContactRepo;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private ResponseApproveContactHelper approveContactHelper;

    private ContactService contactService;
    private User user;
    private ContactRequestDtoV2 contactRequestDtoV2;

    @BeforeEach
    public void init(){
        UserContactEntity userContactEntity = new UserContactEntity();
        contactRequestDtoV2 = new ContactRequestDtoV2();
        UserTestBuilder userTestBuilder = new UserTestBuilder();
        user = userTestBuilder.setEmail().build();
        contactRequestDtoV2.setContact(user.getUsername());
        Mockito.when(userService.findByEmail(contactRequestDtoV2.getContact()))
               .thenReturn(user);
        Mockito.when(userContactRepo.save(userContactEntity))
                .thenReturn(userContactEntity);
        Mockito.when(userContactRepo.save(userContactEntity))
                .thenReturn(userContactEntity);
        contactService = new ContactService(userContactRepo,userService,approveContactHelper);
    }
    
    @Test
    public void getContactEntity(){
        User contactEntity = contactService.getContactEntity(contactRequestDtoV2);
        assertEquals(contactEntity, user);
    }
    
    @Test
    public void approveContact(){
        ApproveContactDto contact = new ApproveContactTestBuilder().setContact()
                                                                   .setCode()
                                                                   .build();
        contact.setContact("danilcherep7@gmail.com");
        contact.setCode("123123");
        ResponseApproveContact responseApproveContact = contactService.approveContact(contact);

    }
}
