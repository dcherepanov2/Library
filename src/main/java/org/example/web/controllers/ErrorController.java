package org.example.web.controllers;

import org.example.exception.BookShelfLoginException;
import org.example.exception.FileUploadException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class ErrorController {

    @GetMapping("/404")
    public String get404(){
        return "errors/404";
    }

    @ExceptionHandler(BookShelfLoginException.class)
    public String handleError(Model model, BookShelfLoginException e){
        model.addAttribute("loginFormException", e.getMessage());
        return "errors/404";
    }

    @ExceptionHandler(FileUploadException.class)
    public String handleError505(Model model, FileUploadException e){
        return "errors/505";
    }
}
