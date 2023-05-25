package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.dto.deserializetor.ReviewEditListDtoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@Data
@JsonDeserialize(using = ReviewEditListDtoDeserializer.class)
public class ReviewEditListDto {

    private List<ReviewDto> reviews;

}
