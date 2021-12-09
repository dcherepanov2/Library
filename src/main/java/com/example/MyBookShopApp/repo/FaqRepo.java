package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.other.FaqEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepo extends JpaRepository<FaqEntity,Integer> {
}
