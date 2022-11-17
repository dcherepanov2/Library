package com.example.MyBookShopApp.service.bookServices;

import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.BookReviewLikeDto;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewLikeRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewRepo;
import com.example.MyBookShopApp.repo.userrepos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BookReviewService {

    private final BookReviewRepo bookReviewRepo;
    private final BookReviewLikeRepo bookReviewLikeRepo;

    private final UserRepo userRepo;

    @Autowired
    public BookReviewService(BookReviewRepo bookReviewRepo, BookReviewLikeRepo bookReviewLikeRepo, UserRepo userRepo) {
        this.bookReviewRepo = bookReviewRepo;
        this.bookReviewLikeRepo = bookReviewLikeRepo;
        this.userRepo = userRepo;
    }

    public List<BookReview> reviewEntitiesBySlugBook(String slug){
        return bookReviewRepo.findBookReviewsByBookId(slug);
    }

    public boolean likeOrDislikeCommentBySlug(Principal principal, BookReviewLikeDto bookReviewLikeDto) {
        User user = userRepo.findByUsername(principal.getName());
        if (bookReviewLikeDto.getValue() == 0) {
            bookReviewLikeRepo.deleteBookReviewLikeEntitiesByUserIdAndReviewId(Math.toIntExact(user.getId()), bookReviewLikeDto.getReviewid());
            return true;
        } else {
            BookReviewLikeEntity bookReviewLikeEntity =
                    bookReviewLikeRepo.findBookReviewLikeEntityByUserIdAndReviewId(Math.toIntExact(user.getId()), bookReviewLikeDto.getReviewid());
            if(bookReviewLikeEntity != null && bookReviewLikeDto.getValue() != bookReviewLikeEntity.getValue()){
                bookReviewLikeRepo.deleteBookReviewLikeEntitiesByUserIdAndReviewId(Math.toIntExact(user.getId()), bookReviewLikeDto.getReviewid());
                bookReviewLikeEntity.setTime(LocalDateTime.now());
                bookReviewLikeEntity.setReviewId(bookReviewLikeDto.getReviewid());
                bookReviewLikeEntity.setUserId(Math.toIntExact(user.getId()));
                bookReviewLikeEntity.setValue(bookReviewLikeDto.getValue());
                bookReviewLikeRepo.save(bookReviewLikeEntity);
                return true;
            }
            else if(bookReviewLikeEntity == null){
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
        for(Map.Entry<Integer,Integer> one: ratingTable.entrySet()){
            sum = sum + (one.getKey() * one.getValue());
            count ++;
        }
        if(count>0){
            return sum/count;
        }
        return 0;
    }
}
