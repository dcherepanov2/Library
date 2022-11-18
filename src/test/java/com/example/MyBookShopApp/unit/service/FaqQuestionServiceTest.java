package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.configuration.MockitoApplicationContext;
import com.example.MyBookShopApp.data.other.FaqEntity;
import com.example.MyBookShopApp.service.otherServices.FaqQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FaqQuestionServiceTest {

    @Spy
    private MockitoApplicationContext mockitoApplicationContext;
    private List<FaqEntity> faqQuestions;

    private FaqQuestionService faqQuestionService;

    @BeforeEach
    public void init(){
        faqQuestionService = (FaqQuestionService) mockitoApplicationContext.getBean(FaqQuestionService.class);
        faqQuestions = (List<FaqEntity>) mockitoApplicationContext.getFieldTestClassByName("faqQuestions", FaqQuestionService.class);
    }

    @Test
    public void getAllFaq(){
        List<FaqEntity> allQuestions = faqQuestionService.getAllQuestions();
        assertEquals(allQuestions, faqQuestions);
    }
}
