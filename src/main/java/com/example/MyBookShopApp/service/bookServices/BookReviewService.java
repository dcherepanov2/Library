package com.example.MyBookShopApp.service.bookServices;

import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.dto.BookReviewLikeDto;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewLikeRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<BookReview> reviewEntitiesBySlugBook(String slug){
        return bookReviewRepo.findBookReviewsByBookId(slug);
    }

    public boolean likeOrDislikeCommentBySlug(BookReviewLikeDto bookReviewLikeDto) {
        if (bookReviewLikeDto.getValue() == 0) {
            bookReviewLikeRepo.deleteBookReviewLikeEntitiesByUserIdAndReviewId(1, bookReviewLikeDto.getReviewid());//TODO: затычка для пользователя переделать в 8 модуле
            return true;
        } else {
            BookReviewLikeEntity bookReviewLikeEntity =
                    bookReviewLikeRepo.findBookReviewLikeEntityByUserIdAndReviewId(1, bookReviewLikeDto.getReviewid());
            if(bookReviewLikeEntity != null && bookReviewLikeDto.getValue() != bookReviewLikeEntity.getValue()){
                bookReviewLikeRepo.deleteBookReviewLikeEntitiesByUserIdAndReviewId(1, bookReviewLikeDto.getReviewid());
                bookReviewLikeEntity.setTime(LocalDateTime.now());
                bookReviewLikeEntity.setReviewId(bookReviewLikeDto.getReviewid());
                bookReviewLikeEntity.setUserId(1);
                bookReviewLikeEntity.setValue(bookReviewLikeDto.getValue());
                bookReviewLikeRepo.save(bookReviewLikeEntity);
                return true;
            }
            else if(bookReviewLikeEntity == null){
                bookReviewLikeEntity = new BookReviewLikeEntity();
                bookReviewLikeEntity.setTime(LocalDateTime.now());
                bookReviewLikeEntity.setReviewId(bookReviewLikeDto.getReviewid());
                bookReviewLikeEntity.setUserId(1);
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
        for(Map.Entry<Integer,Integer> one: ratingTable.entrySet()){
            sum = sum + (one.getKey() * one.getValue());
            count += one.getValue();
        }
        if(count>0){
            return Math.round(sum/count);
        }
        return 0;
    }
}
