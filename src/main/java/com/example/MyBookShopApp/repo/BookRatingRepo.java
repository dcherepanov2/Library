package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.links.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRatingRepo extends JpaRepository<BookRating, Integer> {
    @Query(
            value = "SELECT rb1.id,rb1.book_id, rb1.value, rb1.user_id FROM book AS b1 INNER JOIN rating_book AS rb1 ON b1.slug = :slug AND rb1.id = b1.id",
            nativeQuery = true
    )
    List<BookRating> getBookRatingBySlug(@Param("slug")String slug);
}
