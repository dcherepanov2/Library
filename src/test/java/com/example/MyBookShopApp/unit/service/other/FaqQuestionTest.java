//package com.example.MyBookShopApp.unit.service.other;
//
//import com.example.MyBookShopApp.data.other.DocumentEntity;
//import com.example.MyBookShopApp.data.other.FaqEntity;
//import com.example.MyBookShopApp.repo.otherrepos.FaqRepo;
//import com.example.MyBookShopApp.service.otherServices.FaqQuestionService;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.assertNotNull;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@TestPropertySource("/application_test.properties")
//public class FaqQuestionTest {
//
//    @Autowired
//    private FaqQuestionService faqQuestionService;
//    @Autowired
//    private FaqRepo faqQuestionRepo;
//
//    @BeforeEach
//    public void injectNotNull(){
//        assertNotNull(faqQuestionService);
//        assertNotNull(faqQuestionRepo);
//    }
//
//    @BeforeEach
//    public void saveObject(){
//        FaqEntity faqEntity = new FaqEntity();
//        faqEntity.setQuestion("test");
//        faqEntity.setAnswer("test");
//        faqEntity.setSortIndex(0);
//        faqQuestionRepo.save(faqEntity);
//    }
//
//    @Test
//    public void getAll(){
//        assertNotNull(faqQuestionService.getAllQuestions());
//    }
//}
