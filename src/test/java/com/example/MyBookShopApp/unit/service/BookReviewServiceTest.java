package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.configuration.MockitoApplicationContext;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.BookReviewLikeDto;
import com.example.MyBookShopApp.service.bookServices.BookReviewService;
import com.example.MyBookShopApp.utils.PrincipalImplTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BookReviewServiceTest {

    @Spy
    private MockitoApplicationContext mockitoApplicationContext;
    private BookReviewService bookReviewService;

    private Book book;
    private BookReview bookReview;
    private User user;
    private BookReviewLikeEntity bookReviewLikeEntity;

    Map<Integer, Integer> map = new HashMap<Integer, Integer>(){{
        put(1,1);
        put(2,2);
    }};
    @BeforeEach
    public void init(){
        bookReviewService = (BookReviewService) mockitoApplicationContext.getBean(BookReviewService.class);
        book = (Book) mockitoApplicationContext.getFieldTestClassByName("book", BookReviewService.class);
        bookReview = (BookReview) mockitoApplicationContext.getFieldTestClassByName("bookReview", BookReviewService.class);
        bookReviewLikeEntity = (BookReviewLikeEntity) mockitoApplicationContext.getFieldTestClassByName("bookReviewLikeEntity", BookReviewService.class);
        user = (User) mockitoApplicationContext.getFieldTestClassByName("user", BookReviewService.class);
    }

    @Test
    public void reviewEntitiesBySlugBook(){
        List<BookReview> bookReviewsByBookId = bookReviewService.reviewEntitiesBySlugBook(book.getSlug());
        assertEquals(bookReviewsByBookId,Collections.singletonList(bookReview));
    }

    @Test
    public void likeOrDislikeCommentBySlug(){
        PrincipalImplTest principal = new PrincipalImplTest();
        BookReviewLikeDto bookReviewLikeDto = new BookReviewLikeDto();
        bookReviewLikeDto.setValue((short) 2);
        bookReviewLikeDto.setReviewid(Math.toIntExact(user.getId()));
        boolean result = bookReviewService.likeOrDislikeCommentBySlug(principal, bookReviewLikeDto);
        assertTrue(result);
    }

    @Test
    public void getLikeOrDislikeCommentBySlug(){
        List<BookReviewLikeEntity> bookReviewLikeEntitiesByReviewId = bookReviewService.getLikeOrDislikeCommentBySlug(bookReviewLikeEntity.getUserId());
        assertEquals(bookReviewLikeEntitiesByReviewId, Collections.singletonList(bookReviewLikeEntity));
    }

    @Test
    public void getRatingMedium(){
        Integer ratingMedium = bookReviewService.getRatingMedium(map);
        assertEquals(ratingMedium, 2);
    }
}
