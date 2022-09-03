package com.example.MyBookShopApp.repo.userrepos;

import com.example.MyBookShopApp.data.user.UserGoogle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleUserDetailsRepo extends JpaRepository<UserGoogle,String> {
}
