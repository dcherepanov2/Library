package com.example.MyBookShopApp.service.userServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.repo.bookrepos.Book2UserRepo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ViewService {
    private final Book2UserRepo book2UserRepo;

    @Autowired
    public ViewService(Book2UserRepo book2UserRepo, UserServiceImpl userService) {
        this.book2UserRepo = book2UserRepo;
        this.userService = userService;
    }

    private final UserServiceImpl userService;

    @Value("${books.viewed.date.interval.month}")
    private Integer month;

    public Page<Book2UserEntity> getPageInViewed(User user, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        LocalDateTime toDate = LocalDateTime.now();
        LocalDateTime fromDate = LocalDateTime.now().minusMonths(month);
        return book2UserRepo.getPageInViewed(Math.toIntExact(user.getId()), fromDate, toDate, nextPage);
    }

    public void saveViewBook(JwtUser user, Book book) {
        User byHash = userService.findByHash(user.getHash());
        if (byHash != null) {
            boolean book2UserEntity = byHash.getBook2UserEntities().stream()
                    .anyMatch(x -> x.getBookId().equals(book.getId()));
            if (book2UserEntity)
                return;
        }
        Book2UserEntity book2UserEntity = new Book2UserEntity();
        book2UserEntity.setBookId(book.getId());
        book2UserEntity.setUserId(Math.toIntExact(user.getId()));
        book2UserEntity.setTime(LocalDateTime.now());
        book2UserEntity.setTypeId(5);
        book2UserRepo.save(book2UserEntity);
    }
}
