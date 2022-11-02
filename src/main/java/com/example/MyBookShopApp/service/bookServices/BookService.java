package com.example.MyBookShopApp.service.bookServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.BookRating;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.dto.CommentDtoInput;
import com.example.MyBookShopApp.repo.bookrepos.BookRatingRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Autowired
    public BookService(BookRepo books, BookRatingRepo bookRating, BookReviewRepo bookReviewRepo, BookReviewService bookReviewService) {
        this.books = books;
        this.bookRating = bookRating;
        this.bookReviewRepo = bookReviewRepo;
        this.bookReviewService = bookReviewService;
    }

    public List<Book> getPopularBooksData(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset,limit);
        List<Book> response = books.findByMostPopular(pageable);
        if(response.size() == 0){
            return books.findAll(pageable).getContent();
        }
        return response;
    }

    public List<Book> getSearchQuery(String name, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        return books.findByTitleContaining(name, nextPage).getContent();
    }

    public List<Book> getFilterBooksByDate(Date to, Date from, Integer offset, Integer limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return books.findBooksByDatePublicBetween(to,from,pageable).getContent();
    }

    public List<Book>  getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        //TODO: написать функци. которая будет искать все рекомендованные книги
        return books.findAll(nextPage).getContent();
    }
    //TODO: протестировать весь функционал, посмотреть нужны эти методы или нет, где используются
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

    public void changeRateBookBySlug(String slug, Integer rate) {
        List<BookRating> booksBySlug = bookRating.getBookRatingBySlug(slug);
        if(booksBySlug.size()>1){
            bookRating.deleteBookRatingByUserId(1);//TODO: затычка исправить
        }
        Book book = books.findBookBySlug(slug);
        BookRating bookRate = new BookRating();
        bookRate.setUserId(1);
        bookRate.setValue(rate);
        bookRate.setBook_id(book.getId());
        bookRating.save(bookRate);
    }

    public  Map<Integer, Integer> getBookRateTableBySlug(String slug){
        List<BookRating> booksBySlug = bookRating.getBookRatingBySlug(slug);
        Map<Integer, Integer> rating = new HashMap<Integer, Integer>(){{
            put(1,0);
            put(2,0);
            put(3,0);
            put(4,0);
            put(5,0);
        }};
        for(BookRating bookRating:booksBySlug){
            if(bookRating.getValue()<=1){
                int one = rating.get(1);
                rating.put(1,++one);
            }
            else if(bookRating.getValue()<=2){
                int one = rating.get(2);
                rating.put(2,++one);
            }
            else if(bookRating.getValue()<3.0){
                int one = rating.get(3);
                rating.put(3,++one);
            }
            else if(bookRating.getValue()<4.0){
                int one = rating.get(4);
                rating.put(4,++one);
            }
            else if(bookRating.getValue()<=5.0){
                int one = rating.get(5);
                rating.put(5,++one);
            }
        }
        return rating;
    }

    public void putComment(CommentDtoInput commentDto, String slug) {
        Book book = books.findBookBySlug(slug);
        BookReview bookReview = new BookReview();
        bookReview.setBookId(book.getId());
        bookReview.setUserId(1);//TODO: исправить при добавлении учетной записи в 8 модуле (затычка)
        bookReview.setText(commentDto.getDescription());
        bookReview.setTime(LocalDateTime.now());
        bookReviewRepo.save(bookReview);
        book.getBooksReview().add(bookReview);
        bookReviewService.reviewEntitiesBySlugBook(slug);
    }
}
