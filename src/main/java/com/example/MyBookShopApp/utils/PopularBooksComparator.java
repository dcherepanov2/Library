package com.example.MyBookShopApp.utils;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.book.links.BookRating;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

public class PopularBooksComparator implements Comparator<Book> {
    @Override
    public int compare(Book o1, Book o2) {
        long countFirstBook = 0;
        long countSecondBook = 0;
        long countViewedFirstBook = 0;
        long countViewedSecondBook = 0;
        long countKeptFirstBook = 0;
        long countPostponedFirstBook = 0;
        long countKeptSecondBook = 0;
        long countPostponedSecondBook = 0;
        if (o1.getBookJoinUsers() != null && o1.getBookJoinUsers().size() != 0) {
            countSecondBook = o1.getBookJoinUsers().stream()
                    .map(x -> x.getBook2UserEntities()
                            .stream()
                            .filter(y -> y.getTypeId().equals(1) || y.getTypeId().equals(2))).count();
            countPostponedFirstBook = o1.getBookJoinUsers().stream()
                    .map(x -> x.getBook2UserEntities()
                            .stream()
                            .filter(y -> y.getTypeId().equals(4))).count();
            countKeptFirstBook = o1.getBookJoinUsers().stream()
                    .map(x -> x.getBook2UserEntities()
                            .stream()
                            .filter(y -> y.getTypeId().equals(3))).count();
            countViewedFirstBook = o1.getBookJoinUsers().stream()
                    .map(x -> x.getBook2UserEntities()
                            .stream()
                            .filter(y -> y.getTypeId().equals(5))).count();
        }
        if (o2.getBookJoinUsers() != null && o2.getBookJoinUsers().size() != 0) {
            countSecondBook = o2.getBookJoinUsers().stream()
                    .map(x -> x.getBook2UserEntities()
                            .stream()
                            .filter(y -> y.getTypeId().equals(1) || y.getTypeId().equals(2))).count();
            countPostponedFirstBook = o2.getBookJoinUsers().stream()
                    .map(x -> x.getBook2UserEntities()
                            .stream()
                            .filter(y -> y.getTypeId().equals(4))).count();
            countKeptSecondBook = o2.getBookJoinUsers().stream()
                    .map(x -> x.getBook2UserEntities()
                            .stream()
                            .filter(y -> y.getTypeId().equals(3))).count();
            countViewedSecondBook = o2.getBookJoinUsers().stream()
                    .map(x -> x.getBook2UserEntities()
                            .stream()
                            .filter(y -> y.getTypeId().equals(5))).count();
        }
        return Math.toIntExact((long) ((countSecondBook + countPostponedSecondBook * 0.7 + countKeptSecondBook * 0.4 + countViewedSecondBook * 0.2) -
                (countFirstBook + countPostponedFirstBook * 0.7 + countKeptFirstBook * 0.4 + countViewedFirstBook * 0.2)));
    }
    @Override
    public Comparator<Book> reversed() {
        return Comparator.super.reversed();
    }
}
