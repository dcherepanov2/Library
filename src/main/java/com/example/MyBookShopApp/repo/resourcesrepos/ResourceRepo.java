package com.example.MyBookShopApp.repo.resourcesrepos;

import com.example.MyBookShopApp.data.book.file.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceRepo extends JpaRepository<BookFile, Integer> {
    @Query(
            value = "SELECT bf.id,bf.hash,bf.type_id,bf.balance,bf.path, bf.book_id FROM book2file " +
                    "                    AS b2f INNER JOIN book_file AS bf ON bf.book_id =:id AND bf.book_id = b2f.book_id AND bf.type_id =:type "
            ,nativeQuery = true
    )
    BookFile findPathBookBySlug(@Param("id")Integer id, @Param("type") Integer type);
}
