package com.example.MyBookShopApp.repo.authorrepos;

import com.example.MyBookShopApp.data.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepo extends JpaRepository<Author,Integer> {
    @Query(
            value = "SELECT * FROM author WHERE slug=:slug",
            nativeQuery = true
    )
    Author findBySlug(@Param("slug") String slug);

    Author findAuthorByName(String name);

    List<Author> findAllBySlugIn(List<String> slugs);

}
