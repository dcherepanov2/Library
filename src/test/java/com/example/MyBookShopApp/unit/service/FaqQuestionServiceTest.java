package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.other.FaqEntity;
import com.example.MyBookShopApp.repo.otherrepos.FaqRepo;
import com.example.MyBookShopApp.service.otherServices.FaqQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FaqQuestionServiceTest {

    private List<FaqEntity> faqQuestions;

    private FaqQuestionService faqQuestionService;

    @BeforeEach
    public void init(){
        FaqRepo faqRepo = Mockito.mock(FaqRepo.class);
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
