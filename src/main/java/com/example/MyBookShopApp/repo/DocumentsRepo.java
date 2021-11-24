package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepo extends JpaRepository<Document, Integer> {
}
