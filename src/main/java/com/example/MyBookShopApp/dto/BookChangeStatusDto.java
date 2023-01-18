package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.enums.BookStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BookChangeStatusDto implements AopDto{
    private BookStatus status;
}
