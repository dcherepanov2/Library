package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.service.senders.ValidationService;
import com.example.MyBookShopApp.utils.BindingResultTestImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationServiceTest {
    private ValidationService validationService;

    @BeforeEach
    public void initialize(){
        validationService = new ValidationService();
    }

    @Test
    public void isPhone(){
        boolean phone = validationService.isPhone("+7 (705) 416-50-82");
        assertTrue(phone);
    }

    @Test
    public void isEmail(){
        boolean email = validationService.isEmail("danil@gmail.com");
        assertTrue(email);
    }

    @Test
    public void validate(){
        BindingResultTestImpl errors = new BindingResultTestImpl();
        errors.setFalseErrors();
        validationService.validate(errors);
    }
}
