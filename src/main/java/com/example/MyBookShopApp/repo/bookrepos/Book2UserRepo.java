package com.example.MyBookShopApp.repo.bookrepos;

import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface Book2UserRepo extends JpaRepository<Book2UserEntity, Integer> {

    List<Book2UserEntity> findAllByUserId(Integer id);

    List<Book2UserEntity> findAllByBookId(Integer bookId);

    List<Book2UserEntity> findBook2UserEntityByBookIdAndUserId(Integer bookId, Integer userId);

    @Query(value = "FROM Book2UserEntity WHERE bookId=:bookId AND userId =:userId AND (typeId = 3 OR typeId = 4)")
    List<Book2UserEntity> findBook2UserEntityByBookIdAndUserIdAndTypeIdEquals3And4(@Param("bookId") Integer bookId, @Param("userId") Integer userId);

    List<Book2UserEntity> findBook2UserEntityByUserIdAndTypeId(Integer id, Integer typeId);

    @Query(value = "from Book2UserEntity WHERE userId=:user_id AND typeId=5 AND time BETWEEN :fromDate AND :toDate ORDER BY time DESC",
            countQuery = "SELECT COUNT(*) from Book2UserEntity WHERE userId=:user_id AND typeId=5 AND time BETWEEN :fromDate AND :toDate")
    Page<Book2UserEntity> getPageInViewed(Integer user_id, LocalDateTime fromDate, LocalDateTime toDate, Pageable nextPage);
}
