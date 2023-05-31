package com.example.MyBookShopApp.service.cms;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.dto.CommentDtoInput;
import com.example.MyBookShopApp.dto.ReviewDto;
import com.example.MyBookShopApp.dto.ReviewEditListDto;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ChangeReviewBooksService {

    private final BookReviewRepo bookReviewRepo;


    @Autowired
    public ChangeReviewBooksService(BookReviewRepo bookReviewRepo) {
        this.bookReviewRepo = bookReviewRepo;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void editBookReview(BookReview bySlug, CommentDtoInput commentDtoInput) {
        bySlug.setText(commentDtoInput.getDescription());
        bookReviewRepo.save(bySlug);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void editListBook(ReviewEditListDto reviews, Book bookBySlug) {
        List<BookReview> bookReviews = bookBySlug.getBooksReview();
        List<ReviewDto> reviewDtos = reviews.getReviews();

        // Создаем Map для быстрого доступа к элементам по их id
        Map<Integer, ReviewDto> reviewDtoMap = reviewDtos.stream()
                .collect(Collectors.toMap(ReviewDto::getId, Function.identity(), (existing, replacement) -> replacement));

        // Заменяем элементы списка bookReviews, если их id есть в reviewDtoMap
        for (BookReview bookReview : bookReviews) {
            ReviewDto reviewDto = reviewDtoMap.get(bookReview.getId());
            if (reviewDto != null) {
                bookReview.setText(reviewDto.getText());
            }
        }
        bookReviewRepo.saveAll(bookReviews);
    }
}
