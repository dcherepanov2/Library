package com.example.MyBookShopApp.repo.userrepos;


import com.example.MyBookShopApp.data.user.JwtLogoutToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtBlacklistRepo extends JpaRepository<JwtLogoutToken, Long> {
    JwtLogoutToken findByName(String name);
}
