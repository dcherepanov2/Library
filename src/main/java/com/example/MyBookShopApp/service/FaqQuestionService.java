package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.FaqQuestion;
import com.example.MyBookShopApp.repo.FaqRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqQuestionService {
    private final FaqRepo faqRepo;

    @Autowired
    public FaqQuestionService(FaqRepo faqRepo) {
        this.faqRepo = faqRepo;
    }

    public List<FaqQuestion> getAllQuestions() {
        return faqRepo.findAll();
    }
}
