package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.other.FaqEntity;
import com.example.MyBookShopApp.repo.otherrepos.FaqRepo;
import com.example.MyBookShopApp.service.otherServices.FaqQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FaqQuestionServiceTest {

    @MockBean
    private FaqRepo faqRepo;
    private List<FaqEntity> faqQuestions;

    private FaqQuestionService faqQuestionService;

    @BeforeEach
    public void init(){
        faqQuestions = new ArrayList<>(Collections.singletonList(new FaqEntity()));
        Mockito.when(faqRepo.findAll()).thenReturn(faqQuestions);
        faqQuestionService = new FaqQuestionService(faqRepo);
    }

    @Test
    public void getAllFaq(){
        List<FaqEntity> allQuestions = faqQuestionService.getAllQuestions();
        assertEquals(allQuestions, faqQuestions);
    }
}
