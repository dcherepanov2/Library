package com.example.MyBookShopApp.utils;

import com.example.MyBookShopApp.data.user.User;

public class UserTestBuilder {
    private final User user;

    public UserTestBuilder() {
        this.user = new User();
        user.setId(1L);
        user.setUsername("test");
    }

    public UserTestBuilder setEmail(){
        user.setUsername("danilcherep7@gmail.com");
        return this;
    }
    public User build(){
        return user;
    }
}
