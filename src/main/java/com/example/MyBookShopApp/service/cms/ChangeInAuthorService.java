package com.example.MyBookShopApp.service.cms;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.dto.AuthorEditDto;
import com.example.MyBookShopApp.repo.authorrepos.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangeInAuthorService {
    private final AuthorRepo authorRepo;


    @Autowired
    public ChangeInAuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    public void editAuthor(AuthorEditDto request, Author author){
        if(request.getName() != null)
            author.setName(request.getName());
        if(request.getDescription() != null)
            author.setDescription(request.getDescription());
        authorRepo.save(author);
    }
}
