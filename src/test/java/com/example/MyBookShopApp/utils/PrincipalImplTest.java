package com.example.MyBookShopApp.utils;

import java.security.Principal;

public class PrincipalImplTest implements Principal {

    private final String name = "test";
    @Override
    public String getName() {
        return name;
    }
}
