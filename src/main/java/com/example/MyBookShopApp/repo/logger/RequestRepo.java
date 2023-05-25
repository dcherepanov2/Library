package com.example.MyBookShopApp.repo.logger;

import com.example.MyBookShopApp.data.logger.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RequestRepo extends JpaRepository<RequestEntity,Long> {

}
