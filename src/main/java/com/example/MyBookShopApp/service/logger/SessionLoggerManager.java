package com.example.MyBookShopApp.service.logger;

import com.example.MyBookShopApp.data.logger.RequestEntity;
import com.example.MyBookShopApp.data.logger.ResponseEntity;
import com.example.MyBookShopApp.data.logger.SessionEntity;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class SessionLoggerManager {
    private SessionEntity sessionEntity;
    private RequestEntity requestEntity;
    private ResponseEntity responseEntity;
}
