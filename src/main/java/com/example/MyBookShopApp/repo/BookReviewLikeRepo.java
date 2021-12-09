package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface BookReviewLikeRepo extends JpaRepository<BookReviewLikeEntity, Integer> {

    List<BookReviewLikeEntity> findBookReviewLikeEntitiesByReviewId(Integer id);

    BookReviewLikeEntity findBookReviewLikeEntityByUserIdAndReviewId(Integer user_id, Integer review_id);

    @Modifying
    @Transactional
    void deleteBookReviewLikeEntitiesByUserIdAndReviewId(Integer user_id, Integer review_id);
}
