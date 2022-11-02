//package com.example.MyBookShopApp.unit.service.other;
//
//import com.example.MyBookShopApp.data.other.DocumentEntity;
//import com.example.MyBookShopApp.repo.otherrepos.DocumentsRepo;
//import com.example.MyBookShopApp.service.otherServices.DocumentService;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@TestPropertySource("/application_test.properties")
//public class DocumentServiceTest {
//    @Autowired
//    private DocumentService documentService;
//    @Autowired
//    private DocumentsRepo documentsRepo;
//
//
//    @BeforeEach
//    public void injectNotNull(){
//        assertNotNull(documentService);
//    }
//
//    @BeforeEach
//    public void saveObject(){
//        DocumentEntity documentEntity = new DocumentEntity();
//        documentEntity.setTitle("test");
//        documentEntity.setText("test");
//        documentEntity.setSlug("test");
//        documentEntity.setSortIndex(0);
//        documentsRepo.save(documentEntity);
//    }
//
//    @Test
//    public void findAll(){
//        assertNotNull(documentService.getAllDocuments());
//    }
//}
