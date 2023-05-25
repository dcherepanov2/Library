package com.example.MyBookShopApp.dto;
import lombok.Data;


@Data
public class AuthorAddRequest {
    private String photo;
    private String name;
    private String description;
}
