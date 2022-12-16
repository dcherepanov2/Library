package com.example.MyBookShopApp.repo.logger;

import com.example.MyBookShopApp.data.logger.ThrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThrowLoggerRepo extends JpaRepository<ThrowEntity, Long> {
}
