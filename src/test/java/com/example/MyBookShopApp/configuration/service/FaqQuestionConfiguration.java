package com.example.MyBookShopApp.configuration.service;

import com.example.MyBookShopApp.data.other.FaqEntity;
import com.example.MyBookShopApp.repo.otherrepos.FaqRepo;
import com.example.MyBookShopApp.service.otherServices.FaqQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FaqQuestionConfiguration {
    private List<FaqEntity> faqQuestions;

    private FaqQuestionService faqQuestionService;

    public FaqQuestionService initialize(){
        FaqRepo faqRepo = Mockito.mock(FaqRepo.class);
        faqQuestions = new ArrayList<>(Collections.singletonList(new FaqEntity()));
        Mockito.when(faqRepo.findAll()).thenReturn(faqQuestions);
        return faqQuestionService = new FaqQuestionService(faqRepo);
    }
}
