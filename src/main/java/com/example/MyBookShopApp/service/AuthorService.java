package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.AuthorRepo;
import com.example.MyBookShopApp.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;

    @Autowired
    public AuthorService(AuthorRepo authorRepo, BookRepo bookRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
    }

    public  Map<Character, List<Author>> getSortAuthor(){
        List<Author> authors = (List<Author>)authorRepo.findAll();
        return authors.stream()
                .collect(Collectors.groupingBy(Author::firstLetter));
    }

    public Author getBySlug(String slug) {
        return authorRepo.findBySlug(slug);
    }

    public List<Book> findBooksByAuthor(String slug) {
        Pageable nextPage = PageRequest.of(0,20);
        return bookRepo.findBooksByAuthors(slug, nextPage);
    }
}
