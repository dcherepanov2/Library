package com.example.MyBookShopApp.aop;

import com.example.MyBookShopApp.data.logger.RequestEntity;
import com.example.MyBookShopApp.data.logger.SessionEntity;
import com.example.MyBookShopApp.service.logger.SessionLoggerManager;
import com.example.MyBookShopApp.service.logger.SessionLoggerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AspectLogger {
    private final ObjectMapper objectMapper;

    private final  SessionLoggerService serviceSession;

    private final SessionLoggerManager sessionLoggerManager;

    @Autowired
    public AspectLogger(ObjectMapper objectMapper, SessionLoggerService serviceSession, SessionLoggerManager sessionLoggerManager) {
        this.objectMapper = objectMapper;
        this.serviceSession = serviceSession;
        this.sessionLoggerManager = sessionLoggerManager;
    }

    @Pointcut("within(com.example.MyBookShopApp.controllers..*) && @annotation(org.springframework.web.bind.annotation.GetMapping) || " +
              "within(com.example.MyBookShopApp.controllers..*) && @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void pointcut(){}

    @Before(value = "pointcut()")
    @SneakyThrows
    public void beforeDuration(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        GetMapping getMapping = signature.getMethod().getAnnotation(GetMapping.class);
        PostMapping postMapping = signature.getMethod().getAnnotation(PostMapping.class);
        Map<String, Object> parameters = getParameters(joinPoint);
        Gson gson = new Gson();
        if(getMapping != null){
            RequestEntity requestEntity = serviceSession.saveRequestHttpInDatabase(Arrays.toString(getMapping.value()),gson.toJson(parameters));
            sessionLoggerManager.setSessionEntity(requestEntity.getSessionEntity());
            sessionLoggerManager.setRequestEntity(requestEntity);
        }
        else if (postMapping != null) {
            RequestEntity requestEntity = serviceSession.saveRequestHttpInDatabase(Arrays.toString(postMapping.value()),gson.toJson(parameters));
            sessionLoggerManager.setSessionEntity(requestEntity.getSessionEntity());
            sessionLoggerManager.setRequestEntity(requestEntity);
        }
    }

    @AfterReturning(pointcut = "pointcut()", returning = "page")
    public void logResponse(JoinPoint joinPoint, String page){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        GetMapping getMapping = signature.getMethod().getAnnotation(GetMapping.class);
        PostMapping postMapping = signature.getMethod().getAnnotation(PostMapping.class);
        if (getMapping != null){
            com.example.MyBookShopApp.data.logger.ResponseEntity responseEntity = serviceSession.saveResponseHttpInDatabase(sessionLoggerManager.getSessionEntity(), page);
            sessionLoggerManager.setSessionEntity(responseEntity.getSessionEntity());
            sessionLoggerManager.setResponseEntity(responseEntity);
        }
        else if (postMapping != null) {
            com.example.MyBookShopApp.data.logger.ResponseEntity responseEntity = serviceSession.saveResponseHttpInDatabase(sessionLoggerManager.getSessionEntity(),page);
            sessionLoggerManager.setSessionEntity(responseEntity.getSessionEntity());
            sessionLoggerManager.setResponseEntity(responseEntity);
        }
    }
    @AfterReturning(pointcut = "pointcut()", returning = "entity")
    @SneakyThrows
    public void logMethodAfter(JoinPoint joinPoint, ResponseEntity<?> entity) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        GetMapping getMapping = signature.getMethod().getAnnotation(GetMapping.class);
        PostMapping postMapping = signature.getMethod().getAnnotation(PostMapping.class);
        if (getMapping != null){
            com.example.MyBookShopApp.data.logger.ResponseEntity responseEntity = serviceSession.saveResponseHttpInDatabase(sessionLoggerManager.getSessionEntity(), objectMapper.writeValueAsString(entity));
            sessionLoggerManager.setSessionEntity(responseEntity.getSessionEntity());
            sessionLoggerManager.setResponseEntity(responseEntity);
        }
        else if (postMapping != null) {
            com.example.MyBookShopApp.data.logger.ResponseEntity responseEntity = serviceSession.saveResponseHttpInDatabase(sessionLoggerManager.getSessionEntity(), objectMapper.writeValueAsString(entity));
            sessionLoggerManager.setSessionEntity(responseEntity.getSessionEntity());
            sessionLoggerManager.setResponseEntity(responseEntity);
        }
    }
    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        HashMap<String, Object> map = new HashMap<>();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], joinPoint.getArgs()[i]);
        }
        map.remove("bindingResult");
        return map;
    }
}
