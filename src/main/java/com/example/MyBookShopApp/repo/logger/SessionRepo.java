package com.example.MyBookShopApp.repo.logger;

import com.example.MyBookShopApp.data.logger.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepo extends JpaRepository<SessionEntity,Long> {

}
