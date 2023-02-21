//package com.example.MyBookShopApp.aop;
//
//import com.example.MyBookShopApp.data.logger.RequestEntity;
//import com.example.MyBookShopApp.dto.AopDto;
//import com.example.MyBookShopApp.service.logger.SessionLoggerManager;
//import com.example.MyBookShopApp.service.logger.SessionLoggerService;
//import com.example.MyBookShopApp.service.logger.ThrowLoggerService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.Arrays;
//
//@Aspect
//@Component
//public class AspectLogger {
//    private final ObjectMapper objectMapper;
//
//    private final SessionLoggerService serviceSession;
//
//    private final SessionLoggerManager sessionLoggerManager;
//
//    private final ThrowLoggerService throwLoggerService;
//
//
//    @Autowired
//    public AspectLogger(ObjectMapper objectMapper, SessionLoggerService serviceSession, SessionLoggerManager sessionLoggerManager, ThrowLoggerService throwLoggerService) {
//        this.objectMapper = objectMapper;
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
//        this.serviceSession = serviceSession;
//        this.sessionLoggerManager = sessionLoggerManager;
//        this.throwLoggerService = throwLoggerService;
//    }
//
//    @Pointcut("within(com.example.MyBookShopApp.controllers..*) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
//    public void pointcut() {}
//
//    @Pointcut("within(com.example.MyBookShopApp.controllers..*) && @annotation(org.springframework.web.bind.annotation.PostMapping)")
//    public void pointcutPost() {}
//
//    @Around("pointcut()")
//    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
//        MethodSignature url = (MethodSignature) pjp.getSignature();
//        GetMapping getMapping = url.getMethod().getAnnotation(GetMapping.class);
//        Object[] args = Arrays.stream(pjp.getArgs()).filter(x -> x instanceof AopDto).toArray();
//        RequestEntity requestEntity = serviceSession.saveRequestHttpInDatabase(Arrays.toString(getMapping.value()),objectMapper.writeValueAsString(args));
//        sessionLoggerManager.setSessionEntity(requestEntity.getSessionEntity());
//        sessionLoggerManager.setRequestEntity(requestEntity);
//        Object proceed = pjp.proceed();
//        com.example.MyBookShopApp.data.logger.ResponseEntity responseEntity = serviceSession.saveResponseHttpInDatabase(sessionLoggerManager.getSessionEntity(), objectMapper.writeValueAsString(proceed), 200);
//        sessionLoggerManager.setResponseEntity(responseEntity);
//        return pjp.proceed();
//    }
//
//    @Around("pointcutPost()")
//    public Object applicationPost(ProceedingJoinPoint pjp) throws Throwable {
//        MethodSignature url = (MethodSignature) pjp.getSignature();
//        PostMapping getMapping = url.getMethod().getAnnotation(PostMapping.class);
//        Object[] args = Arrays.stream(pjp.getArgs()).filter(x -> x instanceof AopDto).toArray();
//        RequestEntity requestEntity = serviceSession.saveRequestHttpInDatabase(Arrays.toString(getMapping.value()),objectMapper.writeValueAsString(args));
//        sessionLoggerManager.setSessionEntity(requestEntity.getSessionEntity());
//        sessionLoggerManager.setRequestEntity(requestEntity);
//        Object proceed = pjp.proceed();
//        com.example.MyBookShopApp.data.logger.ResponseEntity responseEntity = serviceSession.saveResponseHttpInDatabase(sessionLoggerManager.getSessionEntity(), objectMapper.writeValueAsString(proceed), 200);
//        sessionLoggerManager.setResponseEntity(responseEntity);
//        return pjp.proceed();
//    }
//
//    @AfterThrowing(pointcut = "within(com.example.MyBookShopApp.controllers..*) && @annotation(org.springframework.web.bind.annotation.GetMapping) || " +
//                              "within(com.example.MyBookShopApp.controllers..*) && @annotation(org.springframework.web.bind.annotation.PostMapping)",
//                   throwing = "ex")
//    public void logThrowInDatabase(Exception ex){
//        throwLoggerService.createThrowEntity(sessionLoggerManager.getSessionEntity(), ex.getMessage());
//    }
//}
