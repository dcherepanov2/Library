package com.example.MyBookShopApp.exception;

import java.text.ParseException;

public interface ExceptionLibrary {
    String checkAll() throws ParseException;
    String checkOffsetAndLimit();
    String checkCase();
}
