package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookStoreUserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(
            value = "SELECT * FROM users WHERE id IN (SELECT user_id FROM user_contact WHERE contact = :email)"
            ,nativeQuery = true
    )
    UserEntity findUserEntityByEmail(@Param("email")String email);

    @Query(
            value = "SELECT uc1.code FROM user_contact AS uc1 INNER JOIN users AS u1 ON uc1.contact = :contact AND uc1.user_id = u1.id ORDER BY reg_time DESC limit 1  "
            ,nativeQuery = true
    )//TODO: добавить тайп в запрос и функцию
    String getCodeUserByContact(@Param("contact")String contact);
}
