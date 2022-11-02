package com.example.MyBookShopApp.repo.bookrepos;

import com.example.MyBookShopApp.data.book.file.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFilesRepo extends JpaRepository<BookFile, Integer> {
}
