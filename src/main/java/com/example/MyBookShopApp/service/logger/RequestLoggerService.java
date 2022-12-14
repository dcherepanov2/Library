package com.example.MyBookShopApp.service.logger;

import com.example.MyBookShopApp.data.logger.RequestEntity;
import com.example.MyBookShopApp.data.logger.SessionEntity;
import com.example.MyBookShopApp.repo.logger.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestLoggerService{
    private final RequestRepo requestRepo;

    @Autowired
    public RequestLoggerService(RequestRepo requestRepo) {
        this.requestRepo = requestRepo;
    }

    public RequestEntity createRequestLog(SessionEntity sessionEntity, String body){
        RequestEntity requestEntity = new RequestEntity(body, sessionEntity);
        return requestRepo.save(requestEntity);
    }
}
