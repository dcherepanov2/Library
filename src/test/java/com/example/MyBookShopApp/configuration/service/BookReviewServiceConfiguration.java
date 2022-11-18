package com.example.MyBookShopApp.configuration.service;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewLikeRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewRepo;
import com.example.MyBookShopApp.repo.userrepos.UserRepo;
import com.example.MyBookShopApp.service.bookServices.BookReviewService;
import com.example.MyBookShopApp.utils.PrincipalImplTest;
import com.example.MyBookShopApp.utils.UserTestBuilder;
import org.mockito.Mockito;

import java.util.Collections;

public class BookReviewServiceConfiguration {
    private BookReviewLikeEntity bookReviewLikeEntity;
    private User user;
    private Book book;
    private BookReview bookReview;

    private BookReviewService bookReviewService;

    public BookReviewService initialize(){
        book = new Book();
        bookReview = new BookReview();
        bookReviewLikeEntity = new BookReviewLikeEntity();
        BookReviewRepo bookReviewRepo = Mockito.mock(BookReviewRepo.class);
        UserRepo userRepo = Mockito.mock(UserRepo.class);
        BookReviewLikeRepo bookReviewLikeRepo = Mockito.mock(BookReviewLikeRepo.class);
        user = new UserTestBuilder()
                                    .setEmail()
                                    .build();
        bookReview.setUserId(Math.toIntExact(user.getId()));
        book.setBooksReview(Collections.singletonList(bookReview));
        Mockito.lenient().when(bookReviewRepo.findBookReviewsByBookId(book.getSlug())).thenReturn(Collections.singletonList(bookReview));
        Mockito.lenient().when(userRepo.findByUsername(new PrincipalImplTest().getName())).thenReturn(user);
        Mockito.lenient().when(bookReviewLikeRepo.findBookReviewLikeEntityByUserIdAndReviewId(bookReview.getId(), Math.toIntExact(user.getId())))
                .thenReturn(bookReviewLikeEntity);
        Mockito.lenient().when(bookReviewLikeRepo.findBookReviewLikeEntitiesByReviewId(bookReviewLikeEntity.getUserId()))
                .thenReturn(Collections.singletonList(bookReviewLikeEntity));
        return bookReviewService = new BookReviewService(bookReviewRepo,bookReviewLikeRepo, userRepo);
    }
}
