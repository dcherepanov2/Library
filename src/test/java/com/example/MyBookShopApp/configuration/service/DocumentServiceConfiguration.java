package com.example.MyBookShopApp.configuration.service;

import com.example.MyBookShopApp.data.other.DocumentEntity;
import com.example.MyBookShopApp.repo.otherrepos.DocumentsRepo;
import com.example.MyBookShopApp.service.otherServices.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;

public class DocumentServiceConfiguration {

    private ArrayList<DocumentEntity> documents;

    private DocumentService documentService;

    public DocumentService initialize(){
        documents = new ArrayList<>(Collections.singletonList(new DocumentEntity()));
        DocumentsRepo documentsRepo = Mockito.mock(DocumentsRepo.class);
        Mockito.when(documentsRepo.findAll())
                .thenReturn(documents);
        return documentService = new DocumentService(documentsRepo);
    }
}
