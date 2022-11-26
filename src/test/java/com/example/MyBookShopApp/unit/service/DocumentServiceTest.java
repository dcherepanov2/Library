package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.other.DocumentEntity;
import com.example.MyBookShopApp.repo.otherrepos.DocumentsRepo;
import com.example.MyBookShopApp.service.otherServices.DocumentService;
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
public class DocumentServiceTest {
    private DocumentService documentService;
    private List<DocumentEntity> documents;

    @BeforeEach
    public void initialize(){
        documents = new ArrayList<>(Collections.singletonList(new DocumentEntity()));
        DocumentsRepo documentsRepo = Mockito.mock(DocumentsRepo.class);
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
