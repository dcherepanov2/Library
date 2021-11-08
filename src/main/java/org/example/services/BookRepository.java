package org.example.services;

import org.apache.log4j.Logger;
import org.example.repo.ProjectRepository;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;//используется в 30 строчке
    private final NamedParameterJdbcTemplate jdbc;
    private int flag = 0;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Book> retrieveAll() {
        List<Book> books = jdbc.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            book.setFilename(rs.getString("filename"));
            return book;
        });
        return new ArrayList<>(books);
    }


    @Override
    public void store(Book book) {
//        book.setId(context.getBean(IdProvider.class).provideId(book));
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("author", book.getAuthor());
        map.addValue("title", book.getTitle());
        map.addValue("size", book.getSize());
        map.addValue("filename", book.getFilename());
        jdbc.update("INSERT INTO books (author,title,size) VALUES('" + book.getAuthor() + "','" + book.getTitle() + "'," + book.getSize() + ")", map);
        logger.info("store new book: " + book);

        // repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        try {
            jdbc.update("DELETE FROM books WHERE id = " + bookIdToRemove, new MapSqlParameterSource().addValue("id", bookIdToRemove));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeItemByAuthor(String author) {
        for (Book book : retrieveAll()) {
            logger.info("find author...");
            logger.info(author);
            if (book.getAuthor().equals(author)) {
                logger.info("remove book completed: " + book);
                jdbc.update("DELETE FROM books WHERE author = " + author, new MapSqlParameterSource().addValue("author", author));
                return true;
            } else {
                logger.info("books not find");
            }
        }
        return false;
    }

    @Override
    public boolean removeItemByTitle(String title) {
        jdbc.update("DELETE FROM books WHERE title = '"+title+"'",new MapSqlParameterSource().addValue("title", title));
//        for (Book book : retrieveAll()) {
//            logger.info("find author...");//
//            logger.info(title);
//            if (book.getTitle().equals(title)) {
//                logger.info("remove book completed: " + book);
//                jdbc.update("DELETE FROM books WHERE title = " + title, new MapSqlParameterSource().addValue("title", title));
//                return true;
//            } else {
//                logger.info("books not find");
//            }
//        }
       return false;
    }

    //найти книги по id
    @Override
    public List<Book> findID(Integer id) {
        List<Book> book = retrieveAll().stream().filter(x->x.getId() == id).collect(Collectors.toList());
        return book;
    }

    @Override
    public List<Book> findTitle(String id) {
        List<Book> list = new ArrayList<>();
        for (Book book : retrieveAll()) {
            if (book.getTitle().contains(id)) {
                list.add(book);
            }
        }
        return list;
    }

    @Override
    public List<Book> findAuthor(String id) {
        List<Book> list = new ArrayList<>();
        for (Book book : retrieveAll()) {
            if (book.getAuthor().contains(id)) {
                list.add(book);
            }
        }
        return list;
    }

    @Override
    public boolean removeAllAuthorByRegex(String author) {
        flag = 0;
        logger.info("regex is " + author);
        retrieveAll().stream().filter(x -> x.getAuthor().matches(author)).forEach(x -> {
            repo.remove(x);
            flag++;
            logger.info(x + "this book is remove");
        });
        return flag > 0;
    }

    @Override
    public boolean removeAllSizeByRegex(String size) {
        flag = 0;
        logger.info("regex is " + size);
        retrieveAll().stream().filter(x -> x.getSize().toString().matches(size)).forEach(x -> {
            repo.remove(x);
            flag++;
            logger.info(x + "this book is remove");
        });
        return flag > 0;
    }

    @Override
    public boolean removeAllTitleByRegex(String title) {
        flag = 0;
        logger.info("regex is " + title);
        retrieveAll().stream().filter(x -> x.getTitle().matches(title)).forEach(x -> {
            jdbc.update("DELETE FROM books WHERE title = '"+title+"'",new MapSqlParameterSource("title",title));
            flag++;
            logger.info(x + "this book is remove");
        });
        return flag > 0;
    }

    @Override
    public List<Book> findAuthorRegex(String string) {
        Stream<Book> stream = retrieveAll().stream().filter(x -> x.getAuthor().equals(string));
        return stream.collect(Collectors.toList());
    }

    @Override
    public List<Book> findSizeRegex(Integer string) {
        try {
            Stream<Book> stream = retrieveAll().stream().filter(x -> x.getSize() == string.intValue());
            return stream.collect(Collectors.toList());
        } catch (Exception e) {
            logger.info("invalid regex size");
            return retrieveAll();
        }
    }

    @Override
    public List<Book> findTitleRegex(String string) {
        Stream<Book> stream = retrieveAll().stream().filter(x -> x.getTitle().equals(string));
        return stream.collect(Collectors.toList());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
