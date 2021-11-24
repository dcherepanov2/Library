package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.repo.BookRepo;
import com.example.MyBookShopApp.repo.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    private final GenreRepo genreRepo;
    private final BookRepo bookRepo;

    @Autowired
    public GenreService(GenreRepo genreRepo, BookRepo bookRepo) {
        this.genreRepo = genreRepo;
        this.bookRepo = bookRepo;
    }

    public List<Genre> getAllGenres(){
        return genreRepo.findAll();
    }

    public List<Book> getAllBookByOneGenre(Integer id) {
        List<Integer> allBooksId = genreRepo.findByIdAllBooks(id);
        List<Book> allBooks = new ArrayList<>();
        for(Integer bookId: allBooksId){
            allBooks.add(bookRepo.getById(bookId));
        }
        return allBooks;
    }
}
