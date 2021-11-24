package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepo extends CrudRepository<Author,Integer>{
    @Query(
            value = "SELECT * FROM author WHERE id=:id",
            nativeQuery = true
    )
    Author findByIdCustom(Integer id);

}
