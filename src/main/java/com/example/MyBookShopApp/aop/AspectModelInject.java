package com.example.MyBookShopApp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.lang.annotation.Annotation;

@Aspect
@Component
public class AspectModelInject {
    @Pointcut("execution(public * *(@org.springframework.web.bind.annotation.ModelAttribute (*)))")
    public void methodWithAnnotatedParameter() {
    }

    @Around("methodWithAnnotatedParameter()")
    public String blahMethod(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        Annotation[][] annotationMatrix = methodSignature.getMethod().getParameterAnnotations();
        boolean foundModelAttribute = false;
        for (Annotation[] annotations : annotationMatrix) {
            for (Annotation annotation : annotations) {
                if (!(annotation instanceof ModelAttribute))
                    continue;
                ModelAttribute modelAttribute = (ModelAttribute) annotation;
                if ("key".equals(modelAttribute.value()) || "key".equals(modelAttribute.name())) {
                    if (!foundModelAttribute) {
                        System.out.println(thisJoinPoint);
                        foundModelAttribute = true;
                    }
                    System.out.println("  " + modelAttribute);
                }
            }
        }
        return (String) thisJoinPoint.proceed();
    }
}
