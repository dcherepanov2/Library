package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.dto.deserializetor.ReviewEditListDtoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonDeserialize(using = ReviewEditListDtoDeserializer.class)
public class ReviewEditListDto {

    @NotEmpty
    @Size(min = 1)
    private List<ReviewDto> reviews;

}
