package com.example.MyBookShopApp.service.bookServices;

import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.dto.BookReviewLikeDto;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewLikeRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewRepo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BookReviewService {

    private final BookReviewRepo bookReviewRepo;
    private final BookReviewLikeRepo bookReviewLikeRepo;

    @Autowired
    public BookReviewService(BookReviewRepo bookReviewRepo, BookReviewLikeRepo bookReviewLikeRepo) {
        this.bookReviewRepo = bookReviewRepo;
        this.bookReviewLikeRepo = bookReviewLikeRepo;
    }

    public List<BookReview> reviewEntitiesBySlugBook(String slug) {
        return bookReviewRepo.findBookReviewsByBookId(slug);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    public boolean likeOrDislikeCommentBySlug(JwtUser user, BookReviewLikeDto bookReviewLikeDto) {
        if (bookReviewLikeDto.getValue() == 0) {
            bookReviewLikeRepo.deleteBookReviewLikeEntitiesByUserIdAndReviewId(Math.toIntExact(user.getId()), bookReviewLikeDto.getReviewid());
            return true;
        } else {
            BookReviewLikeEntity bookReviewLikeEntity =
                    bookReviewLikeRepo.findBookReviewLikeEntityByUserIdAndReviewId(Math.toIntExact(user.getId()), bookReviewLikeDto.getReviewid());
            if (bookReviewLikeEntity != null && bookReviewLikeDto.getValue() != bookReviewLikeEntity.getValue()) {
                bookReviewLikeRepo.deleteBookReviewLikeEntitiesByUserIdAndReviewId(Math.toIntExact(user.getId()), bookReviewLikeDto.getReviewid());
                bookReviewLikeEntity.setTime(LocalDateTime.now());
                bookReviewLikeEntity.setReviewId(bookReviewLikeDto.getReviewid());
                bookReviewLikeEntity.setUserId(Math.toIntExact(user.getId()));
                bookReviewLikeEntity.setValue(bookReviewLikeDto.getValue());
                bookReviewLikeRepo.save(bookReviewLikeEntity);
                return true;
            } else if (bookReviewLikeEntity == null) {
                bookReviewLikeEntity = new BookReviewLikeEntity();
                bookReviewLikeEntity.setTime(LocalDateTime.now());
                bookReviewLikeEntity.setReviewId(bookReviewLikeDto.getReviewid());
                bookReviewLikeEntity.setUserId(Math.toIntExact(user.getId()));
                bookReviewLikeEntity.setValue(bookReviewLikeDto.getValue());
                bookReviewLikeRepo.save(bookReviewLikeEntity);
                return true;
            }
        }
        return false;
    }

    public List<BookReviewLikeEntity> getLikeOrDislikeCommentBySlug(Integer id) {
        return bookReviewLikeRepo.findBookReviewLikeEntitiesByReviewId(id);
    }

    public Integer getRatingMedium(Map<Integer, Integer> ratingTable) {
        int sum = 0;
        int count = 0;
        for (Map.Entry<Integer, Integer> one : ratingTable.entrySet()) {
            sum = sum + (one.getKey() * one.getValue());
            count++;
        }
        if (count > 0) {
            return sum / count;
        }
        return 0;
    }

    public Double calcRateComment(Integer id) {
        double a = bookReviewRepo.calcRateBookBySlug(id, 1) * 2;
        Double b = bookReviewRepo.calcRateBookBySlug(id, -1);
        return b != null ? a * 2 / b : a * 2;
    }
}
