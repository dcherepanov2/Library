package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.configuration.MockitoApplicationContext;
import com.example.MyBookShopApp.data.other.DocumentEntity;
import com.example.MyBookShopApp.repo.otherrepos.DocumentsRepo;
import com.example.MyBookShopApp.service.otherServices.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {
    @Spy
    private MockitoApplicationContext mockitoApplicationContext;
    private DocumentService documentService;
    private List<DocumentEntity> documents;

    @BeforeEach
    public void initialize(){
        documentService = (DocumentService) mockitoApplicationContext.getBean(DocumentService.class);
        documents = (List<DocumentEntity>) mockitoApplicationContext.getFieldTestClassByName("documents", DocumentService.class);
    }

    @Test
    public void getAllDocuments(){
        List<DocumentEntity> allDocuments = documentService.getAllDocuments();
        assertEquals(allDocuments, documents);
    }
}
