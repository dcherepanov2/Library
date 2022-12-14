package com.example.MyBookShopApp.service.logger;

import com.example.MyBookShopApp.data.logger.ResponseEntity;
import com.example.MyBookShopApp.data.logger.SessionEntity;
import com.example.MyBookShopApp.repo.logger.ResponseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseLoggerService {
    private final ResponseRepo responseRepo;

    @Autowired
    public ResponseLoggerService(ResponseRepo responseRepo) {
        this.responseRepo =  responseRepo;
    }

    public ResponseEntity createResponseLog(SessionEntity sessionEntity, String body){
        ResponseEntity requestEntity = new ResponseEntity(body, sessionEntity);
        return responseRepo.save(requestEntity);
    }
}
