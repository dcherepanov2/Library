package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public interface BookRepo extends JpaRepository<Book,Integer> {
    List<Book> findByRecommendedIsTrue();

    @Query(
            value = "SELECT * FROM book ORDER by popular DESC",
            nativeQuery = true
    )
    List<Book> findByMostPopular();

    List<Book> findByTitleContaining(String title);

    @Query(
            value = "SELECT * FROM book WHERE date_public <:afterDate AND date_public >:beforeDate",
            nativeQuery = true
    )
    List<Book> findByDatePublic(
            @Param("afterDate")  @Temporal(TemporalType.TIMESTAMP) Date afterDate,
            @Param("beforeDate") @Temporal(TemporalType.TIMESTAMP) Date beforeDate
    );
}
