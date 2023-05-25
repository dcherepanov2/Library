package com.example.MyBookShopApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoHandlerFound(NoHandlerFoundException e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", e.getLocalizedMessage());
        response.put("class error", e.getClass().getName());
        response.put("http status", String.valueOf(HttpStatus.NOT_FOUND));
        return response;
    }

    @ExceptionHandler(BookFileException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> bookFileNotFound() {
        return new HashMap<>();
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMediaTypeNotSupportedException.class,
            RecentBookException.class,
            BookException.class,
            AuthorException.class,
            BadRequestException.class,
            BookReviewException.class,
            CommentInputException.class,
            JwtLogoutTokenNotFound.class,
            ChangeBookException.class,
            RegistrationException.class,
            UserInsufficientBalance.class,
            BookWasBoughtException.class,
            AddArchiveException.class,
            BadRequestException.class,
            ResponseApproveContactException.class,
            TagException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> badRequest(Exception ex, HttpServletRequest request) throws ParseException {
        HashMap<String, String> response = new HashMap<>();
        String error = null;
        RecentBookException recentBookException = new RecentBookException(request);
        if (request.getRequestURI().contains("/books/recent"))
            error = recentBookException.checkAll();
        if(error == null)
            error = ex.getMessage();
        response.put("result", "false");
        response.put("error", error);
        return response;
    }

    @ExceptionHandler({
            BindException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> badRequestBind(){
        HashMap<String, String> response = new HashMap<>();
        response.put("result", "false");
        response.put("error", "Проверьте введенную информацию");
        return response;
    }
}