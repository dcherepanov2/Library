package com.example.MyBookShopApp.repo.userrepos;

import com.example.MyBookShopApp.data.enums.ContactType;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserContactRepo extends JpaRepository<UserContactEntity, Integer> {
    //TODO: написать свой Query этот работает некорректно
    @Query(value = "SELECT * FROM user_contact WHERE contact = :contact AND code = :code"
          ,nativeQuery = true)
    UserContactEntity findByContact(String contact, Long code);
    @Query(value = "SELECT * FROM user_contact WHERE contact =:contact ORDER BY codetime DESC LIMIT 1"
            ,nativeQuery = true)
    UserContactEntity findByContactOrderByCodeTimeDesc(@Param("contact") String contact);//метод нужен для тестирования

    @Query(value = "from UserContactEntity WHERE contact =:contact AND type =:type order by codeTime DESC",
           countQuery = "select count(*) from UserContactEntity WHERE contact =:contact AND type =:type")
    Page<UserContactEntity> findUserContactEntitiesByContactAndCodeTime(@Param("contact")String contact, @Param("type") ContactType type, Pageable pageable);

    @Query(value = "from UserContactEntity WHERE contact =:contact AND approved = 1 AND userId != null order by codeTime DESC",
            countQuery = "select count(*) from UserContactEntity WHERE contact =:contact AND approved = 1")
    Page<UserContactEntity> findUserContactEntitiesApprovedByContactName(@Param("contact")String contact, Pageable pageable);
}
