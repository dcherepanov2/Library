package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.service.userServices.helpers.UserHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserHelperTest {

    @Spy
    private UserHelper userHelper;

    @Test
    public void generateCode(){
        long number = Long.parseLong(userHelper.generateCode());
        assertTrue(number>100000);
        assertTrue(number<1000000);
    }

    @Test
    public void generateHash(){
        User user = new User();
        String hash = userHelper.generateHash(user);
        assertNotNull(hash);
    }
}
