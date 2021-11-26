package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.repo.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepo authorRepo;

    @Autowired
    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    public  Map<Character, List<Author>> getSortAuthor(){
        List<Author> authors = (List<Author>)authorRepo.findAll();
        return authors.stream()
                .collect(Collectors.groupingBy(Author::firstLetter));
    }

    public Author getById(Integer id) {
        return authorRepo.findByIdCustom(id);
    }
}
