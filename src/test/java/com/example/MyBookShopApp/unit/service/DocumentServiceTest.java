package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.other.DocumentEntity;
import com.example.MyBookShopApp.repo.otherrepos.DocumentsRepo;
import com.example.MyBookShopApp.service.otherServices.DocumentService;
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
public class DocumentServiceTest {
    @MockBean
    private DocumentsRepo documentsRepo;
    private ArrayList<DocumentEntity> documents;

    private DocumentService documentService;
    @BeforeEach
    public void init(){
        documents = new ArrayList<>(Collections.singletonList(new DocumentEntity()));
        Mockito.when(documentsRepo.findAll())
               .thenReturn(documents);
        documentService = new DocumentService(documentsRepo);
    }

    @Test
    public void getAllDocuments(){
        List<DocumentEntity> allDocuments = documentService.getAllDocuments();
        assertEquals(allDocuments, documents);
    }
}
