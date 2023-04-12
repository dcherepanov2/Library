package com.example.MyBookShopApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContactMessageDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("theme")
    private String theme;
    @JsonProperty("message")
    private String message;

    @JsonProperty("email")
    private String email;
}
