package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.dto.ResponseApproveContact;
import com.example.MyBookShopApp.enums.ErrorCodeResponseApproveContact;
import com.example.MyBookShopApp.service.userServices.helpers.ResponseApproveContactHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ResponseApproveContactHelperTest {

    private ResponseApproveContactHelper responseApproveContactHelper;

    @BeforeEach
    public void init(){
        responseApproveContactHelper = new ResponseApproveContactHelper();
    }

    @Test
    public void createOldCodeObject(){
        ResponseApproveContact oldCodeObject = responseApproveContactHelper.createOldCodeObject();
        assertEquals(oldCodeObject.getReturnResponse(), false);
        assertEquals(oldCodeObject.getResult(), false);
        assertEquals(oldCodeObject.getError(), ErrorCodeResponseApproveContact.OLD_CODE.getMessage());
    }

    @Test
    public void createIncorrectErrorCodeObject(){
        ResponseApproveContact incorrectErrorCodeObject = responseApproveContactHelper.createIncorrectErrorCodeObject();
        assertEquals(incorrectErrorCodeObject.getReturnResponse(), false);
        assertEquals(incorrectErrorCodeObject.getResult(), false);
        assertEquals(incorrectErrorCodeObject.getError(), ErrorCodeResponseApproveContact.INCORRECT_ERROR_CODE.getMessage());
    }

    @Test
    public void createNumberOfInputAttemptSObject(){
        ResponseApproveContact incorrectErrorCodeObject = responseApproveContactHelper.createNumberOfInputAttemptSObject();
        assertEquals(incorrectErrorCodeObject.getReturnResponse(), false);
        assertEquals(incorrectErrorCodeObject.getResult(), false);
        assertEquals(incorrectErrorCodeObject.getError(), ErrorCodeResponseApproveContact.NUMBER_OF_INPUT_ATTEMPTS.getMessage());
    }
}
