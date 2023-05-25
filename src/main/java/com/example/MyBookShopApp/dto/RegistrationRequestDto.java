package com.example.MyBookShopApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Getter
@Setter
public class RegistrationRequestDto implements AopDto{

    public RegistrationRequestDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @NotBlank(message = "Username not should be null")
    @Size(min = 6,max = 20, message = "Username should be between 6 and 20 character")
    private String username;

    @JsonProperty("phone")
    private String phone;

    @Email
    @JsonProperty("email")
    private String email;

}
