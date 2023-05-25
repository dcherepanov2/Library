package com.example.MyBookShopApp.repo.tagrepos;

import com.example.MyBookShopApp.data.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepo extends JpaRepository<Tag, Integer> {

    Tag findBySlug(String slug);

    @Query(value = "SELECT * FROM tag ORDER BY RANDOM() LIMIT 40", nativeQuery = true)
    List<Tag> findTagsRandom();

    @Query(value = "SELECT AVG(click) FROM tag", nativeQuery = true)
    Double avgClick();

    List<Tag> findAllBySlugIn(List<String> tags);

    Tag findByName(String name);
}
