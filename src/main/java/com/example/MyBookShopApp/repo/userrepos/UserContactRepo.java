package com.example.MyBookShopApp.repo.userrepos;

import com.example.MyBookShopApp.data.user.UserContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContactRepo extends JpaRepository<UserContactEntity, Integer> {
    //TODO: написать свой Query этот работает некорректно
    UserContactEntity findByCodeAndContactOrderByCodeTimeDesc(Long code, String contact);
}
