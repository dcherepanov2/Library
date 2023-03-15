package com.example.MyBookShopApp.utils;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.BookRating;

import java.util.Comparator;
import java.util.OptionalInt;

public class PopularBooksComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        int countFirstBook = 0;
        int countSecondBook = 0;
        if (o1.getBookRatings().size() != 0) {
            countFirstBook = o1.getBookRatings().stream().map(BookRating::getValue)
                    .reduce(Integer::sum)
                    .orElse(0) / o1.getBookRatings().size();
        }
        if (o2.getBookRatings().size() != 0) {
            countSecondBook = o2.getBookRatings().stream().map(BookRating::getValue)
                    .reduce(Integer::sum)
                    .orElse(0) / o2.getBookRatings().size();
        }
        return countSecondBook - countFirstBook;
    }

    //TODO: Реализация согласно документации, пока разбираюсь
    public void test(Book o1, Book o2) {
        int gradeO1 = 0;
        int gradeO2 = 0;
        if (o1.getBookJoinUsers().size() == 0) {
            gradeO1 = o1.getBookJoinUsers().stream().mapToInt(x -> 1).reduce(Integer::sum).orElse(0);
        }
        if (o2.getBookJoinUsers().size() != 0) {
            gradeO2 = o2.getBookJoinUsers().stream().mapToInt(x -> 1).reduce(Integer::sum).orElse(0);
        }
    }

    @Override
    public Comparator<Book> reversed() {
        return Comparator.super.reversed();
    }
}
