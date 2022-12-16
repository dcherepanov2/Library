package com.example.MyBookShopApp.service.logger;

import com.example.MyBookShopApp.data.logger.SessionEntity;
import com.example.MyBookShopApp.data.logger.ThrowEntity;
import com.example.MyBookShopApp.repo.logger.ThrowLoggerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThrowLoggerService {

    private final ThrowLoggerRepo throwLoggerRepo;

    @Autowired
    public ThrowLoggerService(ThrowLoggerRepo throwLoggerRepo) {
        this.throwLoggerRepo = throwLoggerRepo;
    }

    public ThrowEntity createThrowEntity(SessionEntity sessionEntity, String message){
        ThrowEntity throwEntity = new ThrowEntity(sessionEntity,message);
        return throwLoggerRepo.save(throwEntity);
    }
}
