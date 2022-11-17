package com.example.MyBookShopApp.service.bookServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.BookRating;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.CommentDtoInput;
import com.example.MyBookShopApp.repo.bookrepos.BookRatingRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewRepo;
import com.example.MyBookShopApp.repo.userrepos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Scope("application")
public class BookService {

    private final BookRepo books;
    private final BookRatingRepo bookRating;
    private final BookReviewRepo bookReviewRepo;
    private final BookReviewService bookReviewService;

    private final UserRepo userRepo;

    @Autowired
    public BookService(BookRepo books, BookRatingRepo bookRating, BookReviewRepo bookReviewRepo, BookReviewService bookReviewService, UserRepo userRepo) {
        this.books = books;
        this.bookRating = bookRating;
        this.bookReviewRepo = bookReviewRepo;
        this.bookReviewService = bookReviewService;
        this.userRepo = userRepo;
    }

    public List<Book> getPopularBooksData(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<Book> response = books.findByMostPopular(pageable);
        if (response.size() == 0)
            return books.findAll(pageable).getContent();
        return response;
    }

    public List<Book> getSearchQuery(String name, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return books.findByTitleContaining(name, nextPage).getContent();
    }

    public List<Book> getFilterBooksByDate(Date to, Date from, Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return books.findBooksByDatePublicBetween(to, from, pageable).getContent();
    }

    public List<Book> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        List<Book> byMostPopular = books.findByMostPopular(nextPage);
        if (byMostPopular == null)
            books.findAll(nextPage).getContent();
        return byMostPopular;
    }
//    public List<Book> findBooksByFirstName(String name){
//        return books.findBooksByAuthorFirstNameContaining(name);
//    }
////
////    public List<Book> findBooksByTitle(String title){
////        return books.findBooksByTitleContaining(title);
////    }
////
////    public List<Book> findBooksByPriceBetween(int min, int max){
////        return books.getBooksByPriceBetween(min, max);
////    }
//
//    public List<Book> findBooksByIsBestseller(){
//        return books.getBooksByIsBestseller();
//    }

    //public List<Book> findBooksByMaxDiscount(){
    //    return books.getBooksWithMaxDiscount();
    //}

    public List<Book> getPageOfRecommendedBooks(int offset, int limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        List<Book> byMostPopular = books.findByMostPopular(nextPage);
        if (byMostPopular == null)
            books.findAll(nextPage).getContent();
        return byMostPopular;
    }

    public Book getBookBySlug(String slug) {
        return books.findBookBySlug(slug);
    }

    public List<Book> getAllBooksByTag(String slug, Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return books.findBooksByTag(slug, pageable).stream().sorted(
                Comparator.comparing(Book::getDatePublic)).collect(Collectors.toList());
    }

    public void changeRateBookBySlug(Principal principal, String slug, Integer rate) {
        User byUsername = userRepo.findByUsername(principal.getName());
        List<BookRating> booksBySlug = bookRating.getBookRatingBySlug(slug);
        if (booksBySlug.size() > 1) {
            bookRating.deleteBookRatingByUserId(Math.toIntExact(byUsername.getId()));
        }
        Book book = books.findBookBySlug(slug);
        BookRating bookRate = new BookRating();
        bookRate.setUserId(Math.toIntExact(byUsername.getId()));
        bookRate.setValue(rate);
        bookRate.setBook_id(book.getId());
        bookRating.save(bookRate);
    }

    public Map<Integer, Integer> getBookRateTableBySlug(String slug) {
        List<BookRating> booksBySlug = bookRating.getBookRatingBySlug(slug);
        Map<Integer, Integer> rating = new HashMap<Integer, Integer>() {{
            put(1, 0);
            put(2, 0);
            put(3, 0);
            put(4, 0);
            put(5, 0);
        }};
        for (BookRating bookRating : booksBySlug) {
            if (bookRating.getValue() <= 1) {
                int one = rating.get(1);
                rating.put(1, ++one);
            } else if (bookRating.getValue() <= 2) {
                int one = rating.get(2);
                rating.put(2, ++one);
            } else if (bookRating.getValue() < 3) {
                int one = rating.get(3);
                rating.put(3, ++one);
            } else if (bookRating.getValue() < 4) {
                int one = rating.get(4);
                rating.put(4, ++one);
            } else if (bookRating.getValue() <= 5) {
                int one = rating.get(5);
                rating.put(5, ++one);
            }
        }
        return rating;
    }

    public List<BookReview> putComment(Principal principal,CommentDtoInput commentDto, String slug) {
        User user = userRepo.findByUsername(principal.getName());
        Book book = books.findBookBySlug(slug);
        BookReview bookReview = new BookReview();
        bookReview.setBookId(book.getId());
        bookReview.setUserId(Math.toIntExact(user.getId()));
        bookReview.setText(commentDto.getDescription());
        bookReview.setTime(LocalDateTime.now());
        bookReviewRepo.save(bookReview);
        book.getBooksReview().add(bookReview);
        return bookReviewService.reviewEntitiesBySlugBook(slug);
    }
}
