package com.example.MyBookShopApp.repo.bookrepos;

import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Book2UserRepo extends JpaRepository<Book2UserEntity, Integer> {

    List<Book2UserEntity> findAllByUserId(Integer id);

    List<Book2UserEntity> findBook2UserEntityByBookIdAndUserId(Integer bookId, Integer userId);

    List<Book2UserEntity> findBook2UserEntityByUserIdAndTypeId(Integer id, Integer typeId);
}
