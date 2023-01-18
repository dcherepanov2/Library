package com.example.MyBookShopApp.dto;

public class ProfileResponse implements AopDto{
    private final String name;

    private final Double wallet;


    public ProfileResponse(String name, Double wallet) {
        this.name = name;
        this.wallet = 0.00;//TODO нельзя хранить деньги в Double, переписать этот момент, при переходе на 11 модуль
    }
}
