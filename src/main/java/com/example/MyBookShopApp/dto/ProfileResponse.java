package com.example.MyBookShopApp.dto;

import lombok.Data;

@Data
public class ProfileResponse implements AopDto{
    private final String name;

    private final Double wallet;


    public ProfileResponse(String name, Double wallet) {
        this.name = name;
        this.wallet = wallet;
    }
}
