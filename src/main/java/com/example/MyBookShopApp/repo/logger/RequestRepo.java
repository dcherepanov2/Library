package com.example.MyBookShopApp.repo.logger;

import com.example.MyBookShopApp.data.logger.RequestEntity;
import com.example.MyBookShopApp.data.logger.SessionEntity;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RequestRepo extends JpaRepository<RequestEntity,Long> {

}
