package com.example.MyBookShopApp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddBook2UserDto {
    @NotNull(message = "UserSlug must not be null")
    private Long userSlug;

    @NotBlank(message = "BookSlug is required")
    private String bookSlug;
}
