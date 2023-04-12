package com.example.MyBookShopApp.repo.bookrepos;

import com.example.MyBookShopApp.data.book.review.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookReviewRepo extends JpaRepository<BookReview,Integer> {

    @Query(
            value = "SELECT br1.id,br1.book_id,br1.user_id,br1.time,br1.text FROM book AS b1 INNER JOIN book_review AS br1 ON b1.slug =:slug AND br1.book_id = b1.id ORDER BY time DESC"
            ,nativeQuery = true
    )
    List<BookReview> findBookReviewsByBookId(@Param("slug") String slug);

    @Query(
            value = "SELECT br1.id,br1.book_id,br1.user_id,br1.time,br1.text FROM book AS b1" +
                    " INNER JOIN book_review AS br1 ON b1.slug =:slug AND br1.book_id = b1.id AND user_id =:userId",
            nativeQuery = true
    )
    BookReview findRateByBookSlugAndUserId(@Param("userId") int id, @Param("slug") String slug);

    BookReview findBookReviewById(Integer id);

    @Query(value = "SELECT COUNT(*) FROM book_review_like as brk INNER JOIN book_review AS br ON br.id = brk.review_id AND brk.value =:flagLike AND br.id =:id",
            nativeQuery = true)
    Double calcRateBookBySlug(@Param("id") Integer slug, Integer flagLike);
}
