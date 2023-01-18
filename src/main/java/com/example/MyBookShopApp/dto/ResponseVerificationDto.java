package com.example.MyBookShopApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseVerificationDto implements AopDto{
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
}
