package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    @Query(
            value = " SELECT b1.id, b1.is_bestseller, b1.slug, b1.title, b1.image, b1.description, b1.price, b1.discount, b1.pub_date" +
                    " FROM book AS b1 INNER JOIN " +
                    " (SELECT book_id, review_id FROM book_review_like as bk1 INNER JOIN book_review AS bk2 ON bk1.review_id = bk2.id ORDER BY value DESC)" +
                    "AS bk2 ON bk2.book_id = b1.id",
            countQuery = " SELECT COUNT(*)" +
                    " FROM book AS b1 INNER JOIN " +
                    " (SELECT book_id, review_id FROM book_review_like as bk1 INNER JOIN book_review AS bk2 ON bk1.review_id = bk2.id ORDER BY value DESC)" +
                    "AS bk2 ON bk2.book_id = b1.id",
            nativeQuery = true
    )
    List<Book> findByMostPopular(Pageable pageable);

    Page<Book> findByTitleContaining(String title, Pageable nextPage);
//
//    @Query(
//            value = "SELECT * FROM book WHERE pub_date <?1 AND pub_date >?2 limit ?#{#pageable}",
//            countQuery = "SELECT count(*) FROM book WHERE pub_date <?1 AND pub_date >?2 ",
//            nativeQuery = true
//    )
//    Page<Book> findByDatePublic(
//            @Temporal(TemporalType.TIMESTAMP) Date afterDate,
//            @Temporal(TemporalType.TIMESTAMP) Date beforeDate,
//            Pageable pageable
//    );

    Page<Book> findBooksByDatePublicBetween(Date to, Date from, Pageable pageable);

    @Query(
            value = "WITH idAuthors AS (" +
                    "SELECT id FROM author WHERE substring(name,0,strpos(name,' ')) LIKE '%' || :name || '%'" +
                    "), idBook2Author AS(" +
                    "    SELECT * FROM idAuthors AS a1 INNER JOIN book2author ON a1.id = authors_id" +
                    ")" +
                    "SELECT b1.id, b1.is_bestseller, b1.slug, b1.title, b1.image, b1.description, b1.price, b1.discount, b1.pub_date " +
                    "FROM book AS b1 INNER JOIN idBook2Author ON b1.id = book_id ",
            nativeQuery = true
    )
    List<Book> findBooksByAuthorFirstNameContaining(@Param("name") String name);

    List<Book> findBooksByTitleContaining(String title);

    List<Book> getBooksByPriceBetween(Integer min, Integer max);

    @Query(value = "SELECT * FROM book WHERE is_bestseller = 1", nativeQuery = true)
    List<Book> getBooksByIsBestseller();

    @Query(
            value = "SELECT * FROM book WHERE discount = (SELECT MAX(discount) FROM book)"
            , nativeQuery = true
    )
    List<Book> getBooksWithMaxDiscount();

    @Query(
            value = " SELECT b1.id, b1.is_bestseller, b1.slug, b1.title, b1.image, b1.description, b1.price, b1.discount, b1.pub_date " +
                    " FROM book AS b1 INNER JOIN (SELECT * FROM tag AS t1 INNER JOIN book2tag AS bt1 ON t1.slug = :slug AND t1.id = bt1.id) AS btt1 " +
                    " ON b1.id = btt1.book_id",
            nativeQuery = true
    )
    List<Book> findBooksByTag(@Param("slug") String slug, Pageable pageable);

    @Query(
            value = " SELECT COUNT (*)" +
                    " FROM book AS b1 INNER JOIN (SELECT * FROM tag AS t1 INNER JOIN book2tag AS bt1 ON t1.slug = :slug AND t1.id = bt1.id) AS btt1 " +
                    " ON b1.id = btt1.book_id",
            nativeQuery = true
    )
    Integer findBooksByTagCount(@Param("slug") String slug);

    @Query(
            value = " SELECT b1.id, b1.is_bestseller, b1.slug, b1.title, b1.image, b1.description, b1.price, b1.discount, b1.pub_date FROM book AS b1 INNER JOIN " +
                    " (SELECT * FROM author AS a1 INNER JOIN book2author AS b2a1 ON a1.id = b2a1.authors_id AND a1.slug = :slug)" +
                    " AS b2a2 ON b1.id = b2a2.book_id"
            , countQuery = "SELECT COUNT(*) FROM book AS b1 INNER JOIN " +
            " (SELECT * FROM author AS a1 INNER JOIN book2author AS b2a1 ON a1.id = b2a1.authors_id AND a1.slug = :slug)" +
            " AS b2a2 ON b1.id = b2a2.book_id"
            , nativeQuery = true
    )
    List<Book> findBooksByAuthors(@Param("slug") String slug, Pageable pageable);

    Book findBookBySlug(String slug);

    @Query(
            value = " WITH child AS(\n" +
                    " \tSELECT * FROM genre WHERE slug = :slug\n" +
                    "),child2 AS(\n" +
                    " \tSELECT * FROM genre WHERE parent_id IN (SELECT id FROM child)\n" +
                    "), allChild AS (\n" +
                    "\tSELECT * FROM child UNION (SELECT * FROM child2)\n" +
                    ")\n" +
                    "SELECT b2.id, b2.is_bestseller, b2.slug, b2.title, b2.image, b2.description, b2.price, b2.discount, b2.pub_date\n" +
                    "FROM book AS b2 INNER JOIN (SELECT * FROM allChild AS c1 INNER JOIN book2genre AS b2g1 ON c1.id = b2g1.genre_id) \n" +
                    "\t\t\t\t\t\t\t  AS b2g2 ON b2.id = b2g2.book_id  \n",

            countQuery = " WITH child AS(\n" +
                    " \tSELECT * FROM genre WHERE slug = :slug\n" +
                    "),child2 AS(\n" +
                    " \tSELECT * FROM genre WHERE parent_id IN (SELECT id FROM child)\n" +
                    "), allChild AS (\n" +
                    "\tSELECT * FROM child UNION (SELECT * FROM child2)\n" +
                    ")\n" +
                    "SELECT COUNT(*)\n" +
                    "FROM book AS b2 INNER JOIN (SELECT * FROM allChild AS c1 INNER JOIN book2genre AS b2g1 ON c1.id = b2g1.genre_id) \n" +
                    "\t\t\t\t\t\t\t  AS b2g2 ON b2.id = b2g2.book_id  \n",
            nativeQuery = true
    )
    List<Book> findBooksByGenreSlug(@Param("slug") String slug, Pageable pageable);

    @Query(
            value = "SELECT COUNT(*) FROM books",
            nativeQuery = true
    )
    Integer getCountTable();


}
