package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepo extends JpaRepository<GenreEntity,Integer>{

   @Query(
           value = "SELECT * FROM genre WHERE parent_id =:parentId",
           nativeQuery = true
   )
   List<GenreEntity> findByParentId(@Param("parentId") Integer parentId);

   GenreEntity findBySlug(String slug);
}
