package com.example.MyBookShopApp.service.bookServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.book.links.BookRating;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.CommentDtoInput;
import com.example.MyBookShopApp.enums.ErrorMessageResponse;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.exception.BookReviewException;
import com.example.MyBookShopApp.exception.ChangeBookRateException;
import com.example.MyBookShopApp.exception.CommentInputException;
import com.example.MyBookShopApp.repo.bookrepos.Book2UserRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRatingRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewRepo;
import com.example.MyBookShopApp.repo.userrepos.UserRepo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import com.example.MyBookShopApp.utils.PopularBooksComparator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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


    private final Book2UserRepo book2UserRepo;

    private final UserServiceImpl userService;

    @Value("${google.api.key}")
    private String googleApiKey;

    @Autowired
    public BookService(BookRepo books, BookRatingRepo bookRating, BookReviewRepo bookReviewRepo, BookReviewService bookReviewService, UserRepo userRepo, Book2UserRepo book2UserRepo, UserServiceImpl userService) {
        this.books = books;
        this.bookRating = bookRating;
        this.bookReviewRepo = bookReviewRepo;
        this.bookReviewService = bookReviewService;
        this.userRepo = userRepo;
        this.book2UserRepo = book2UserRepo;
        this.userService = userService;
    }

    public List<Book> getPopularBooksData(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<Book> response = books.findByMostPopular(pageable);
        if (response == null || response.size() < limit)
            return sortPopularBook(books.findAll(pageable).getContent());
        return sortPopularBook(response);
    }

    public List<Book> getRecommendedBooks(Integer offset, Integer limit, JwtUser jwtUser) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<Book> response;
        if (jwtUser != null) {
            response = books.recommendedBooksIfUserAuth(Math.toIntExact(jwtUser.getId()));
            List<Book> viewedBooks = books.getAllViewedBooks(Math.toIntExact(jwtUser.getId()));
            response.addAll(viewedBooks);
            List<Book> allReadyResponse = new ArrayList<>();
            if (!response.isEmpty()) {
                Collections.shuffle(response);
                for (int i = offset * limit; i < (offset * limit) + limit; i++) {
                    if (response.size() > i)
                        allReadyResponse.add(response.get(i));
                    else
                        break;
                }
            }
            return allReadyResponse;
        } else
            response = books.findAll(pageable).getContent();
        if (response.isEmpty())
            return books.findAll(pageable).getContent();
        return sortPopularBook(response);
    }

    public List<Book> getPopularBooksDataApi(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return sortPopularBook(books.findByMostPopular(pageable));
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
        List<Book> list = books.findBooksByTag(slug, pageable);
        list.stream().sorted(
                Comparator.comparing(Book::getDatePublic)).forEach(x -> x.setTags(null));
        return list;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    @SneakyThrows
    public void changeRateBookBySlug(JwtUser user, String slug, Integer rate) {
        User byUsername = userService.findByHash(user.getHash());
        if (byUsername == null)
            throw new UsernameNotFoundException("User with hash: " + user.getHash() + " was not found.");
        List<BookRating> booksBySlug = bookRating.getBookRatingBySlugAndUserId(slug, user.getId());
        if (rate > 5 || rate < 1)
            throw new ChangeBookRateException(ErrorMessageResponse.CHANGE_BOOK_RATE.getName());
        if (booksBySlug.size() > 1) {
            bookRating.deleteBookRatingByUserId(Math.toIntExact(byUsername.getId()));
        }
        Book book = books.findBookBySlug(slug);
        if (book == null)
            throw new BookReviewException(ErrorMessageResponse.NOT_FOUND_BOOK.getName());
        BookRating bookRate = new BookRating();
        bookRate.setUserId(Math.toIntExact(byUsername.getId()));
        bookRate.setValue(rate);
        bookRate.setBook_id(book);
        bookRating.save(bookRate);
    }

    public Map<Integer, Integer> getBookRateTableBySlug(String slug) {
        List<BookRating> booksBySlug = bookRating.getBookRatingBySlug(slug);
        Map<Integer, Integer> rating = new HashMap<>();
        rating.put(1, 0);
        rating.put(2, 0);
        rating.put(3, 0);
        rating.put(4, 0);
        rating.put(5, 0);
        for (BookRating x : booksBySlug) {
            if (x.getValue() <= 1) {
                int one = rating.get(1);
                rating.put(1, ++one);
            } else if (x.getValue() <= 2) {
                int one = rating.get(2);
                rating.put(2, ++one);
            } else if (x.getValue() < 3) {
                int one = rating.get(3);
                rating.put(3, ++one);
            } else if (x.getValue() < 4) {
                int one = rating.get(4);
                rating.put(4, ++one);
            } else if (x.getValue() <= 5) {
                int one = rating.get(5);
                rating.put(5, ++one);
            }
        }
        return rating;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    @SneakyThrows
    public List<BookReview> putComment(JwtUser jwtUser, CommentDtoInput commentDto, String slug) {
        if (commentDto == null || commentDto.getDescription() == null || commentDto.getDescription().length() == 0)
            throw new CommentInputException(ErrorMessageResponse.COMMENT_INPUT_NOT_ADDED.getName());
        User user = userRepo.findByHash(jwtUser.getHash());
        if (user == null)
            throw new UsernameNotFoundException("User not found with hash: " + jwtUser.getHash());
        Book book = books.findBookBySlug(slug);
        if (book == null)
            throw new BookReviewException(ErrorMessageResponse.NOT_FOUND_BOOK.getName());
        BookReview bookReview = new BookReview();
        bookReview.setBookId(book);
        bookReview.setUserId(user);
        bookReview.setText(commentDto.getDescription());
        bookReview.setTime(LocalDateTime.now());
        bookReviewRepo.save(bookReview);
        return bookReviewService.reviewEntitiesBySlugBook(slug);
    }

    public List<Book> getBooksBySlugs(List<String> booksSlugPostponed) {
        List<Book> booksLocal = new ArrayList<>();
        booksSlugPostponed.forEach(x -> booksLocal.add(books.findBookBySlug(x)));
        return booksLocal.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Book getBookById(Integer id) {
        if (id != null)
            return books.getById(id);
        return null;
    }

    private List<Book> sortPopularBook(List<Book> books) {
        PopularBooksComparator popularBooksComparator = new PopularBooksComparator();
        return books.stream().sorted(popularBooksComparator).collect(Collectors.toList());
    }

    public void saveAllBook2User(List<Book2UserEntity> booksLocal) {
        book2UserRepo.saveAll(booksLocal);
    }

    public List<Book> findAllBooksByIds(List<Integer> ids) {
        return books.findAllById(ids);
    }

    public Book findBookByTitle(String name) throws BookException {
        Pageable page = PageRequest.of(0, 1);
        Book bookByName = books.findBookByTitle(name, page).stream()
                .findFirst()
                .orElse(null);
        if (bookByName == null)
            throw new BookException("Книга не найдена!");
        return bookByName;
    }

}
