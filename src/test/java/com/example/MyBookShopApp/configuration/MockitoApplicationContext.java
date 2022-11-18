package com.example.MyBookShopApp.configuration;

import com.example.MyBookShopApp.configuration.service.*;
import com.example.MyBookShopApp.service.authorServices.AuthorService;
import com.example.MyBookShopApp.service.bookServices.BookReviewService;
import com.example.MyBookShopApp.service.bookServices.ResourceStorage;
import com.example.MyBookShopApp.service.otherServices.DocumentService;
import com.example.MyBookShopApp.service.otherServices.FaqQuestionService;
import com.example.MyBookShopApp.service.tagServices.TagService;

import java.lang.reflect.Field;

public class MockitoApplicationContext {

    private final AuthorServiceConfiguration authorServiceConfiguration;

    private final BookReviewServiceConfiguration bookReviewServiceConfiguration;

    private final DocumentServiceConfiguration documentServiceConfiguration;

    private final ResourceServiceConfiguration resourceServiceConfiguration;

    private final TagServiceConfiguration tagServiceConfiguration;

    private final FaqQuestionConfiguration faqQuestionConfiguration;
    public MockitoApplicationContext() {
        this.authorServiceConfiguration = new AuthorServiceConfiguration();
        this.documentServiceConfiguration = new DocumentServiceConfiguration();
        this.bookReviewServiceConfiguration = new BookReviewServiceConfiguration();
        this.faqQuestionConfiguration = new FaqQuestionConfiguration();
        this.resourceServiceConfiguration = new ResourceServiceConfiguration();
        this.tagServiceConfiguration = new TagServiceConfiguration();
    }

    public Object getBean(Class<?> clazz){
        if(clazz == AuthorService.class)
            return authorServiceConfiguration.initAuthorService();
        if(clazz == BookReviewService.class)
            return bookReviewServiceConfiguration.initialize();
        if(clazz == DocumentService.class)
            return documentServiceConfiguration.initialize();
        if(clazz == FaqQuestionService.class)
            return faqQuestionConfiguration.initialize();
        if(clazz == ResourceStorage.class)
            return resourceServiceConfiguration.initialize();
        if(clazz == TagService.class)
            return tagServiceConfiguration.initialize();
        throw new IllegalArgumentException("Не существует класса: " + clazz.getName());
    }

    public Object getFieldTestClassByName(String name, Class<?> clazz){
        Object obj = null;
        try{
            if(clazz == AuthorService.class){
                Field field = authorServiceConfiguration.getClass().getDeclaredField(name);
                field.setAccessible(true);
                obj = field.get(authorServiceConfiguration);
            }
            else if(clazz == BookReviewService.class){
                Field field = bookReviewServiceConfiguration.getClass().getDeclaredField(name);
                field.setAccessible(true);
                obj = field.get(bookReviewServiceConfiguration);
            }
            else if(clazz == DocumentService.class){
                Field field = documentServiceConfiguration.getClass().getDeclaredField(name);
                field.setAccessible(true);
                obj = field.get(documentServiceConfiguration);
            }
            else if(clazz == FaqQuestionService.class){
                Field field = faqQuestionConfiguration.getClass().getDeclaredField(name);
                field.setAccessible(true);
                obj = field.get(faqQuestionConfiguration);
            }
            else if(clazz == ResourceStorage.class){
                Field field = resourceServiceConfiguration.getClass().getDeclaredField(name);
                field.setAccessible(true);
                obj = field.get(resourceServiceConfiguration);
            }
            else if(clazz == TagService.class){
                Field field = tagServiceConfiguration.getClass().getDeclaredField(name);
                field.setAccessible(true);
                obj = field.get(tagServiceConfiguration);
            }
        }catch (NoSuchFieldException | IllegalAccessException e){
            throw new AssertionError(e.getMessage());
        }
        if(obj != null)
            return obj;
        throw new IllegalArgumentException("Не существует класса: " + clazz.getName());
    }
}
