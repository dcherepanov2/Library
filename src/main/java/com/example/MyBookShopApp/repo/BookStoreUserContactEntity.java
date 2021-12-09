package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.user.UserContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookStoreUserContactEntity extends JpaRepository<UserContactEntity, Integer> {

}
