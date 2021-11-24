package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepo extends JpaRepository<Genre,Integer>{

    @Query(
            value = "SELECT * FROM book_genres WHERE genre_id =:id",
            nativeQuery = true
    )
   List<Integer> findByIdAllBooks(@Param("id")Integer id);


}
