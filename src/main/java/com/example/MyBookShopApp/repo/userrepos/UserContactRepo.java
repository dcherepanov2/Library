package com.example.MyBookShopApp.repo.userrepos;

import com.example.MyBookShopApp.data.user.UserContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserContactRepo extends JpaRepository<UserContactEntity, Integer> {
    //TODO: написать свой Query этот работает некорректно
    @Query(value = "SELECT * FROM user_contact WHERE contact = :contact AND code = :code"
          ,nativeQuery = true)
    UserContactEntity findByContact(String contact, Long code);
    @Query(value = "SELECT * FROM user_contact WHERE contact =:contact ORDER BY codetime DESC LIMIT 1"
            ,nativeQuery = true)
    UserContactEntity findByContactOrderByCodeTimeDesc(@Param("contact") String contact);//метод нужен для тестирования
}
