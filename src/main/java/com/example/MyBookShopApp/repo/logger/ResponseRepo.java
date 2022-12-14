package com.example.MyBookShopApp.repo.logger;

import com.example.MyBookShopApp.data.logger.ResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepo extends JpaRepository<ResponseEntity, Long> {
}
