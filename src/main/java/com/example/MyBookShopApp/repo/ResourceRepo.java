package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.file.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceRepo extends JpaRepository<BookFile, Integer> {
    @Query(
            value = "SELECT * FROM book_file as bf1 INNER JOIN " +
                    "(SELECT fd1.book_id FROM book AS b1 INNER JOIN file_download AS fd1 ON b1.slug = :slug AND b1.id = fd1.book_id) AS bk1 " +
                    "ON bf1.book_id = bk1.book_id AND type_id = :type"
            ,nativeQuery = true
    )
    BookFile findPathBookBySlug(@Param("slug")String slug, @Param("type") Integer type);
}
