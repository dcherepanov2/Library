package com.example.MyBookShopApp.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UserInfo {
    private Map.Entry<String, Short> emailAndApprove;

    private Map.Entry<String, Short> phoneAndApprove;


}
