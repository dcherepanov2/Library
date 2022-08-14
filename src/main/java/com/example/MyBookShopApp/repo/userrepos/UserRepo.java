package com.example.MyBookShopApp.repo.userrepos;

import com.example.MyBookShopApp.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String name);

    User findByEmail(String email);

}
