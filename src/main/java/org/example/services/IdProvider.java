package org.example.services;

import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.logging.Logger;

public class IdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {

    Logger logger = Logger.getLogger(String.valueOf(IdProvider.class));

    public String provideId(Book book) {
        return this.hashCode() + "_" + book.hashCode();
    }

  private void initIdProvider() {
        logger.info("provider_INIT");
   }

   private void destroyIdProvider() {
       logger.info("provider_DESTROY");
    }

   private  void defaultInit(){
        logger.info("provider_DEFAULT");
    }
     private void defaultDestroy(){
       logger.info("default_DESTROY");
     }

    @Override
    public void afterPropertiesSet(){
        logger.info("provider afterPropertiesSet invoked");
    }

    @Override
    public void destroy(){

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @PreDestroy
    public void preDestroyIdProvider(){

    }

    @PostConstruct
    public void postConstructIdProvider(){

    }
}
