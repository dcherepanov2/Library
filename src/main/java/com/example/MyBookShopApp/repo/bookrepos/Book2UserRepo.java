package com.example.MyBookShopApp.repo.bookrepos;

import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface Book2UserRepo extends JpaRepository<Book2UserEntity, Integer> {

    List<Book2UserEntity> findAllByUserId(Integer id);

    List<Book2UserEntity> findAllByBookId(Integer bookId);

    List<Book2UserEntity> findBook2UserEntityByBookIdAndUserId(Integer bookId, Integer userId);

    List<Book2UserEntity> findBook2UserEntityByUserIdAndTypeId(Integer id, Integer typeId);

    @Query(value = "from Book2UserEntity WHERE userId=:user_id AND typeId=5 AND time BETWEEN :fromDate AND :toDate ORDER BY time DESC",
            countQuery = "SELECT COUNT(*) from Book2UserEntity WHERE userId=:user_id AND typeId=5 AND time BETWEEN :fromDate AND :toDate")
    Page<Book2UserEntity> getPageInViewed(Integer user_id, LocalDateTime fromDate, LocalDateTime toDate, Pageable nextPage);
}
