package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.BookReviewLikeDto;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewLikeRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewRepo;
import com.example.MyBookShopApp.repo.userrepos.UserRepo;
import com.example.MyBookShopApp.service.bookServices.BookReviewService;
import com.example.MyBookShopApp.utils.PrincipalImplTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookReviewServiceTest {

    @MockBean
    private BookReviewRepo bookReviewRepo;

    @MockBean
    private BookReviewLikeRepo bookReviewLikeRepo;

    @MockBean
    private UserRepo userRepo;

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
    public void initialize(){
        book = new Book();
        bookReview = new BookReview();
        bookReviewLikeEntity = new BookReviewLikeEntity();
        user = new User();
        user.setId(1L);
        bookReview.setUserId(Math.toIntExact(user.getId()));
        user.setUsername("test");
        book.setBooksReview(Collections.singletonList(bookReview));
        Mockito.when(bookReviewRepo.findBookReviewsByBookId(book.getSlug())).thenReturn(Collections.singletonList(bookReview));
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(bookReviewLikeRepo.findBookReviewLikeEntityByUserIdAndReviewId(bookReview.getId(), Math.toIntExact(user.getId())))
               .thenReturn(bookReviewLikeEntity);
        Mockito.when(bookReviewLikeRepo.findBookReviewLikeEntitiesByReviewId(bookReviewLikeEntity.getUserId()))
                .thenReturn(Collections.singletonList(bookReviewLikeEntity));
        bookReviewService = new BookReviewService(bookReviewRepo,bookReviewLikeRepo, userRepo);
    }

    @Test
    public void reviewEntitiesBySlugBook(){
        List<BookReview> bookReviewsByBookId = bookReviewService.reviewEntitiesBySlugBook(book.getSlug());
        assertEquals(bookReviewsByBookId,Collections.singletonList(bookReview));
    }

    @Test
    public void likeOrDislikeCommentBySlug(){
        PrincipalImplTest principal = new PrincipalImplTest();
        principal.setName(user.getUsername());
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
