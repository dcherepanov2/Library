package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping()
    public String getAllDocuments(Model model){
        model.addAttribute("allDocuments", documentService.getAllDocuments());
        return "/documents/index";
    }

}
