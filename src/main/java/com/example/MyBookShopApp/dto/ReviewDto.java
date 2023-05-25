package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.dto.deserializetor.ReviewDtoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@JsonDeserialize(using = ReviewDtoDeserializer.class)
public class ReviewDto {

    private final String text;

    private final Integer id;

    private final String time;
    public ReviewDto(BookReview bookReview) {
        this.id =bookReview.getId();
        this.text = bookReview.getText();
        this.time = localDateTimeToReadeableString(bookReview.getTime());
    }

    public ReviewDto(String text, Integer id, String time) {
        this.id = id;
        this.text = text;
        this.time = time;
    }

    private String localDateTimeToReadeableString(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(localDateTime);
    }
}
