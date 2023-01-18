package com.example.MyBookShopApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactConfirmationPayLoad implements AopDto{

    @JsonProperty("code")
    private String code;

    @JsonProperty("contact")
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
