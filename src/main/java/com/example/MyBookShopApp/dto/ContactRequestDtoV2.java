package com.example.MyBookShopApp.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequestDtoV2  implements AopDto{
    @JsonProperty("contact")
    private String contact;
}
