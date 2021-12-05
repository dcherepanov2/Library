package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Scope("application")
public class BookService {
    private final BookRepo books;

    @Autowired
    public BookService(BookRepo books) {
        this.books = books;
    }

    public List<Book> getPopularBooksData(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset,limit);
        return books.findByMostPopular(pageable);
    }

    public List<Book> getSearchQuery(String name, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        List<Book> booksSearch;
        booksSearch = books.findByTitleContaining(name, nextPage).getContent();
        return booksSearch;
    }

    public List<Book> getFilterBooksByDate(Date to, Date from, Integer offset, Integer limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return books.findBooksByDatePublicBetween(to,from,pageable).getContent();
    }

    public List<Book>  getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return books.findAll(nextPage).getContent();
    }

    public List<Book> findBooksByFirstName(String name){
        return books.findBooksByAuthorFirstNameContaining(name);
    }

    public List<Book> findBooksByTitle(String title){
        return books.findBooksByTitleContaining(title);
    }

    public List<Book> findBooksByPriceBetween(int min, int max){
        return books.getBooksByPriceBetween(min, max);
    }

    public List<Book> findBooksByIsBestseller(){
        return books.getBooksByIsBestseller();
    }

    public List<Book> findBooksByMaxDiscount(){
        return books.getBooksWithMaxDiscount();
    }

    public Page<Book> getPageOfRecommendedBooks(int offset, int limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return books.findAll(nextPage);
    }

    public Book getBookBySlug(String slug){
        return books.findBookBySlug(slug);
    }

    public List<Book> getAllBooksByTag(String slug, Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return books.findBooksByTag(slug,pageable).stream().sorted(
                Comparator.comparing(Book::getDatePublic)).collect(Collectors.toList());
    }
}
