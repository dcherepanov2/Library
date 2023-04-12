package com.example.MyBookShopApp.repo.otherrepos;

import com.example.MyBookShopApp.data.book.review.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepo extends JpaRepository<MessageEntity, Long> {
}
