package com.example.MyBookShopApp.repo.bookrepos;

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

    @Query(
            value = "SELECT * FROM book WHERE title = :query " +
                    "UNION (SELECT * FROM book where description = :query)" +
                    "UNION (SELECT * FROM book WHERE id IN(SELECT book_id FROM book2tag AS b2t INNER JOIN  tag AS t1 ON name = :query AND b2t.tag_id = t1.id))" +
                    "UNION(SELECT * FROM book WHERE id IN(SELECT book_id FROM author AS a1 INNER JOIN book2author AS b2a ON name LIKE '%' || :query || '%' AND a1.id = b2a.authors_id))" +
                    "UNION(SELECT * FROM book WHERE id = (SELECT book_id FROM book2genre AS b2g INNER JOIN genre as g1 ON g1.name = :query AND g1.id = b2g.book_id))",
            countQuery = "WITH counts AS (SELECT COUNT(*) FROM book WHERE title = 'Cooking with Stella' " +
                    " UNION (SELECT COUNT(*) FROM book where description = 'Cooking with Stella')" +
                    " UNION (SELECT COUNT(*) FROM book WHERE id IN(SELECT book_id FROM book2tag AS b2t INNER JOIN  tag AS t1 ON name = 'Tres-Zap' AND b2t.tag_id = t1.id))" +
                    " UNION(SELECT COUNT(*)  FROM book WHERE id IN(SELECT book_id FROM author AS a1 INNER JOIN book2author AS b2a ON name LIKE '%' || 'Rosco Log' || '%' AND a1.id = b2a.authors_id))" +
                    " UNION(SELECT COUNT(*)  FROM book WHERE id = (SELECT book_id FROM book2genre AS b2g INNER JOIN genre as g1 ON g1.name = 'Stronghold' AND g1.id = b2g.book_id)))" +
                    "SELECT SUM(count) FROM counts;",
            nativeQuery = true
    )
    Page<Book> findByTitleContaining(String query, Pageable nextPage);

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
            value = "SELECT b1.id, b1.is_bestseller, b1.slug, b1.title, b1.image, b1.description, b1.price, b1.discount, b1.pub_date \n" +
                    "FROM book AS b1 INNER JOIN\n" +
                    "(SELECT book_id FROM tag AS t1 INNER JOIN book2tag AS bt2 ON t1.slug = :slug AND bt2.tag_id = t1.id) AS btt1\n" +
                    "ON b1.id = btt1.book_id",
            countQuery = "SELECT COUNT(*) FROM book AS b1 INNER JOIN\n" +
                    "(SELECT book_id FROM tag AS t1 INNER JOIN book2tag AS bt2 ON t1.slug = 'test' AND bt2.tag_id = t1.id) AS btt1\n" +
                    "ON b1.id = btt1.book_id",
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
            ,nativeQuery = true
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

    List<Book> findBooksBySlugIn(String[] slugs);


    @Query(value =
            "SELECT DISTINCT b.id, b.title, b.description, b.price, b.pub_date, b.discount, b.is_bestseller, b.slug, b.image" +
                    " FROM book b " +
                    "LEFT JOIN (" +
                    "  SELECT book_id, AVG(value) as avg_rating" +
                    "  FROM public.rating_book" +
                    "  GROUP BY book_id" +
                    ") r ON b.id = r.book_id " +
                    "WHERE b.pub_date >= CURRENT_DATE - INTERVAL '1 MONTH'" +
                    "  AND (b.is_bestseller = 1 OR r.avg_rating >= (" +
                    "    SELECT AVG(value) as top_rating" +
                    "    FROM (" +
                    "      SELECT book_id, AVG(value) as value" +
                    "      FROM public.rating_book" +
                    "      GROUP BY book_id" +
                    "      ORDER BY value DESC" +
                    "      LIMIT (SELECT COUNT(*) FROM public.book) / 2" +
                    "    ) t" +
                    "  ))" +
                    "  AND NOT EXISTS (" +
                    "    SELECT 1" +
                    "    FROM public.book2user bu" +
                    "    WHERE bu.book_id = b.id" +
                    "      AND bu.user_id = :userId" +
                    "      AND bu.type_id IN (1, 2)" +
                    "  )" +
                    "LIMIT (SELECT COUNT(*) FROM book) / 2", nativeQuery = true)
    List<Book> recommendedBooksIfUserAuth(@Param("userId") Long id);
}
