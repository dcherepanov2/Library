package com.example.MyBookShopApp.utils;

import com.example.MyBookShopApp.data.user.User;

public class UserTestBuilder {
    private final User user;

    public UserTestBuilder() {
        this.user = new User();
        user.setId(1L);
        user.setUsername("test");
    }

    public User build(){
        return user;
    }
}
