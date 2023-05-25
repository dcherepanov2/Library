package com.example.MyBookShopApp.repo.userrepos;

import com.example.MyBookShopApp.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String name);

    @Query(
            value = "SELECT * FROM users AS u1 INNER JOIN (SELECT user_id FROM user_contact WHERE contact = :contact) AS uc1 ON u1.id = uc1.user_id limit 1",
            nativeQuery = true
    )
    User findByContact(@Param("contact") String contact);

    User findByHash(String hash);
}
