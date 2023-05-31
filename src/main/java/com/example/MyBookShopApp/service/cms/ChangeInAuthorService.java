package com.example.MyBookShopApp.service.cms;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.dto.AuthorEditDto;
import com.example.MyBookShopApp.repo.authorrepos.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeInAuthorService {
    private final AuthorRepo authorRepo;


    @Autowired
    public ChangeInAuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void editAuthor(AuthorEditDto request, Author author) {
        author.setName(request.getName());
        author.setDescription(request.getDescription());
        authorRepo.save(author);
    }
}
