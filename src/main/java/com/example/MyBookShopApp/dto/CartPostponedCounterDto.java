package com.example.MyBookShopApp.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CartPostponedCounterDto {
    private Integer countPostponed = 0;

    private Integer countCart = 0;
}
