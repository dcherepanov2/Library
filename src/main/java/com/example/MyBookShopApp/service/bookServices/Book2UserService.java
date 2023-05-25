package com.example.MyBookShopApp.service.bookServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.repo.bookrepos.Book2UserRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Book2UserService {
    private final Book2UserRepo book2UserRepo;
    private final BookRepo bookRepo;

    @Autowired
    public Book2UserService(Book2UserRepo book2UserRepo, BookRepo bookRepo) {
        this.book2UserRepo = book2UserRepo;
        this.bookRepo = bookRepo;
    }

    public List<Book2UserEntity> getAllBook2User(JwtUser jwtUser) {
        List<Book2UserEntity> book2UserEntityByUserId = null;
        if (jwtUser != null)
            book2UserEntityByUserId = book2UserRepo.findAllByUserId(Math.toIntExact(jwtUser.getId()));
        if (book2UserEntityByUserId != null && !book2UserEntityByUserId.isEmpty())
            return book2UserEntityByUserId;
        else
            return new ArrayList<>();
    }

    public List<Book> getBooksUser(JwtUser jwtUser, Integer typeId) {
        List<Integer> book2UserEntityByUserId = book2UserRepo.findBook2UserEntityByUserIdAndTypeId(Math.toIntExact(jwtUser.getId()), typeId)
                .stream()
                .map(Book2UserEntity::getBookId)
                .distinct()
                .collect(Collectors.toList());
        return bookRepo.findAllById(book2UserEntityByUserId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, RuntimeException.class})
    public void save(JwtUser jwtUser, String slug, Integer typeId) throws BookException {
        Book2UserEntity book2UserEntity = new Book2UserEntity();
        Book bookBySlug = bookRepo.findBookBySlug(slug);
        if (bookBySlug == null)
            throw new BookException("Книга не найдена");
        if (jwtUser == null)
            throw new UsernameNotFoundException("Пользователь не найден.");
        book2UserEntity.setBookId(bookBySlug.getId());
        book2UserEntity.setTypeId(typeId);
        book2UserEntity.setTime(LocalDateTime.now());
        book2UserEntity.setUserId(Math.toIntExact(jwtUser.getId()));
        book2UserRepo.save(book2UserEntity);
    }
}
