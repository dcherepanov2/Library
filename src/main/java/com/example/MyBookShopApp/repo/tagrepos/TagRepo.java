package com.example.MyBookShopApp.repo.tagrepos;

import com.example.MyBookShopApp.data.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepo extends JpaRepository<Tag, Integer> {

    Tag findBySlug(String slug);

    @Query(value = "SELECT * FROM  tag ORDER BY RANDOM() LIMIT 30", nativeQuery = true)
    List<Tag> findTagsRandom();
}
