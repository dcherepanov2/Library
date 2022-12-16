package com.example.MyBookShopApp.aop;

import com.example.MyBookShopApp.data.logger.RequestEntity;
import com.example.MyBookShopApp.service.logger.SessionLoggerManager;
import com.example.MyBookShopApp.service.logger.SessionLoggerService;
import com.example.MyBookShopApp.service.logger.ThrowLoggerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
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
import java.util.logging.Logger;

@Aspect
@Component
public class AspectLogger {
    private final ObjectMapper objectMapper;

    private final SessionLoggerService serviceSession;

    private final SessionLoggerManager sessionLoggerManager;

    private final ThrowLoggerService throwLoggerService;


    @Autowired
    public AspectLogger(ObjectMapper objectMapper, SessionLoggerService serviceSession, SessionLoggerManager sessionLoggerManager, ThrowLoggerService throwLoggerService) {
        this.objectMapper = objectMapper;
        this.serviceSession = serviceSession;
        this.sessionLoggerManager = sessionLoggerManager;
        this.throwLoggerService = throwLoggerService;
    }

    @Pointcut("within(com.example.MyBookShopApp.controllers..*) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        MethodSignature url = (MethodSignature) pjp.getSignature();
        GetMapping getMapping = url.getMethod().getAnnotation(GetMapping.class);
        String className = pjp.getTarget().getClass().toString();
        Object[] array = pjp.getArgs();
        Object[] args = new Object[array.length-1];
        if(array.length > 2){
            for (int i = 1; i < 3; i++){
                args[i-1] = array[i];
            }
        }
        RequestEntity requestEntity = serviceSession.saveRequestHttpInDatabase(Arrays.toString(getMapping.value()),objectMapper.writeValueAsString(args));
        sessionLoggerManager.setSessionEntity(requestEntity.getSessionEntity());
        sessionLoggerManager.setRequestEntity(requestEntity);
        return pjp.proceed();
    }

//    @Before(value = "pointcut()")
//    @SneakyThrows
//    public void beforeDuration(ProceedingJoinPoint joinPoint){
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        GetMapping getMapping = signature.getMethod().getAnnotation(GetMapping.class);
//        PostMapping postMapping = signature.getMethod().getAnnotation(PostMapping.class);
//        Object[] parameters = joinPoint.getArgs();
//        if(getMapping != null){
//            RequestEntity requestEntity = serviceSession.saveRequestHttpInDatabase(Arrays.toString(getMapping.value()),objectMapper.writeValueAsString(parameters));
//            sessionLoggerManager.setSessionEntity(requestEntity.getSessionEntity());
//            sessionLoggerManager.setRequestEntity(requestEntity);
//        }
//        else if (postMapping != null) {
//            RequestEntity requestEntity = serviceSession.saveRequestHttpInDatabase(Arrays.toString(postMapping.value()),objectMapper.writeValueAsString(parameters));
//            sessionLoggerManager.setSessionEntity(requestEntity.getSessionEntity());
//            sessionLoggerManager.setRequestEntity(requestEntity);
//        }
//    }

    @AfterReturning(pointcut = "pointcut()", returning = "page")
    public void logResponse(JoinPoint joinPoint, String page){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        GetMapping getMapping = signature.getMethod().getAnnotation(GetMapping.class);
        PostMapping postMapping = signature.getMethod().getAnnotation(PostMapping.class);
        if (getMapping != null){
            com.example.MyBookShopApp.data.logger.ResponseEntity responseEntity = serviceSession.saveResponseHttpInDatabase(sessionLoggerManager.getSessionEntity(), page,200 );
            sessionLoggerManager.setSessionEntity(responseEntity.getSessionEntity());
            sessionLoggerManager.setResponseEntity(responseEntity);
        }
        else if (postMapping != null) {
            com.example.MyBookShopApp.data.logger.ResponseEntity responseEntity = serviceSession.saveResponseHttpInDatabase(sessionLoggerManager.getSessionEntity(),page,200);
            sessionLoggerManager.setSessionEntity(responseEntity.getSessionEntity());
            sessionLoggerManager.setResponseEntity(responseEntity);
        }
    }

    @AfterThrowing(pointcut = "within(com.example.MyBookShopApp.controllers..*) && @annotation(org.springframework.web.bind.annotation.GetMapping) || " +
                              "within(com.example.MyBookShopApp.controllers..*) && @annotation(org.springframework.web.bind.annotation.PostMapping)",
                   throwing = "ex")
    public void logThrowInDatabase(Exception ex){
        throwLoggerService.createThrowEntity(sessionLoggerManager.getSessionEntity(), ex.getMessage());
    }

    @AfterReturning(pointcut = "pointcut()", returning = "entity")
    @SneakyThrows
    public void logMethodAfter(JoinPoint joinPoint, ResponseEntity<?> entity) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        GetMapping getMapping = signature.getMethod().getAnnotation(GetMapping.class);
        PostMapping postMapping = signature.getMethod().getAnnotation(PostMapping.class);
        if (getMapping != null){
            com.example.MyBookShopApp.data.logger.ResponseEntity responseEntity = serviceSession.saveResponseHttpInDatabase(sessionLoggerManager.getSessionEntity(), objectMapper.writeValueAsString(entity), 200);
            sessionLoggerManager.setSessionEntity(responseEntity.getSessionEntity());
            sessionLoggerManager.setResponseEntity(responseEntity);
        }
        else if (postMapping != null) {
            com.example.MyBookShopApp.data.logger.ResponseEntity responseEntity = serviceSession.saveResponseHttpInDatabase(sessionLoggerManager.getSessionEntity(), objectMapper.writeValueAsString(entity), 200);
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
