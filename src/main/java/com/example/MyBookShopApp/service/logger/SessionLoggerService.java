package com.example.MyBookShopApp.service.logger;

import com.example.MyBookShopApp.data.logger.RequestEntity;
import com.example.MyBookShopApp.data.logger.ResponseEntity;
import com.example.MyBookShopApp.data.logger.SessionEntity;
import com.example.MyBookShopApp.repo.logger.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionLoggerService {
    private final SessionRepo sessionRepo;

    private final RequestLoggerService requestLoggerService;

    private final ResponseLoggerService responseLoggerService;

    @Autowired
    public SessionLoggerService(SessionRepo sessionRepo, RequestLoggerService requestLoggerService, ResponseLoggerService responseLoggerService) {
        this.sessionRepo = sessionRepo;
        this.requestLoggerService = requestLoggerService;
        this.responseLoggerService = responseLoggerService;
    }

    private SessionEntity createSession(String url){
        SessionEntity sessionEntity = new SessionEntity(url);
        return sessionRepo.save(sessionEntity);
    }

    public RequestEntity saveRequestHttpInDatabase(String url, String requestBody){
        SessionEntity sessionEntity = this.createSession(url);
        return requestLoggerService.createRequestLog(sessionEntity,requestBody);
    }

    public ResponseEntity saveResponseHttpInDatabase(SessionEntity sessionEntity, String requestBody){
        return responseLoggerService.createResponseLog(sessionEntity,requestBody);
    }
}
