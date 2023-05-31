package com.example.MyBookShopApp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthorEditDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;
}
