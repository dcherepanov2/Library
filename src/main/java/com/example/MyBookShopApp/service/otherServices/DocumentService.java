package com.example.MyBookShopApp.service.otherServices;

import com.example.MyBookShopApp.data.other.DocumentEntity;
import com.example.MyBookShopApp.repo.otherrepos.DocumentsRepo;
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

    public List<DocumentEntity> getAllDocuments (){
        return documentsRepo.findAll();
    }
}
