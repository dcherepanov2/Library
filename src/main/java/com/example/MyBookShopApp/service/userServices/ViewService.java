package com.example.MyBookShopApp.service.userServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.UserInfo;
import com.example.MyBookShopApp.repo.bookrepos.Book2UserRepo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Service
public class ViewService {
    private final Book2UserRepo book2UserRepo;

    @Value("${books.viewed.date.interval.month}")
    private Integer month;

    public ViewService(Book2UserRepo book2UserRepo) {
        this.book2UserRepo = book2UserRepo;
    }


    public Page<Book2UserEntity> getPageInViewed(User user, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        LocalDateTime toDate = LocalDateTime.now();
        LocalDateTime fromDate = LocalDateTime.now().minusMonths(month);
        return book2UserRepo.getPageInViewed(Math.toIntExact(user.getId()), fromDate, toDate, nextPage);
    }

    public void saveViewBook(JwtUser user, Book book) {
        Book2UserEntity book2UserEntity = new Book2UserEntity();
        book2UserEntity.setBookId(book.getId());
        book2UserEntity.setUserId(Math.toIntExact(user.getId()));
        book2UserEntity.setTime(LocalDateTime.now());
        book2UserEntity.setTypeId(5);
        book2UserRepo.save(book2UserEntity);
    }
}
