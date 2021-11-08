package org.example.exception;

public class FileUploadException extends Exception{
    private final String message;

    public FileUploadException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
