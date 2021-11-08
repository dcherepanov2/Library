package org.example.repo;

import org.example.web.dto.Book;

import java.util.List;


public interface ProjectRepository<T>{
    List<T> retrieveAll();

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByAuthor(String Author);

    boolean removeItemByTitle(String title);

    List<Book> findID(Integer id);

    List<Book> findTitle(String id);

    List<Book> findAuthor(String id);

    boolean removeAllAuthorByRegex(String author) throws Exception;

    boolean removeAllSizeByRegex(String author) throws Exception;

    boolean removeAllTitleByRegex(String author);

    List<Book> findAuthorRegex(String string);

    List<T> findSizeRegex(Integer string);

    List<Book> findTitleRegex(String string);
}
