package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Document;
import com.example.MyBookShopApp.repo.DocumentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    private final DocumentsRepo documentsRepo;

    @Autowired
    public DocumentService(DocumentsRepo documentsRepo) {
        this.documentsRepo = documentsRepo;
    }

    public List<Document> getAllDocuments (){
        return documentsRepo.findAll();
    }
}
