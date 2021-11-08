package org.example.exception;

public class BookShelfLoginException extends Exception  {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }


    public BookShelfLoginException(String message) {
        this.message = message;
    }
}
