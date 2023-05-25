package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.enums.BookStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class BookChangeStatusDto implements AopDto {
    @JsonProperty("booksIds")
    @JsonSetter("booksIds")
    private String booksIds;
    @JsonProperty("status")
    @JsonSetter("status")
    private BookStatus status;
}
