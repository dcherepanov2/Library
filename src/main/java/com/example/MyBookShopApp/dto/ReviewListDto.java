package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.book.Book;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ReviewListDto {
    private final String slug;

    private final List<ReviewDto> reviews;

    public ReviewListDto(Book book) {
        reviews = book.getBooksReview()
                      .stream()
                      .map(ReviewDto::new).collect(Collectors.toList());
        this.slug =book.getSlug();
    }
}
