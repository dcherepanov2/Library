package com.example.MyBookShopApp.exception;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RecentBookException extends Exception implements ExceptionLibrary {

    private HttpServletRequest request;

    public RecentBookException(String message) {
        super(message);
    }

    public RecentBookException(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }

    public String checkDateInput() {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
        String toDate = request.getParameter("to");
        String fromDate = request.getParameter("from");
        try {
            myFormat.parse(toDate);
            myFormat.parse(fromDate);
        } catch (ParseException e) {
            return "Проверьте параметры фильтрации. Некорректный ввод.";
        }
        return null;
    }

    public String checkDateOrder() throws ParseException {
        String toDate = request.getParameter("to");
        String fromDate = request.getParameter("from");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date to = myFormat.parse(toDate);
        Date from = myFormat.parse(fromDate);
        if (to.before(from))
            return "Проверьте параметры фильтрации. Некорректный ввод.";
        return null;
    }

    @Override
    public String checkOffsetAndLimit() {
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        try {
            Integer.parseInt(offset);
            Integer.parseInt(limit);
        } catch (NumberFormatException e) {
            return "Проверьте параметры пагинации. Некорректный ввод.";
        }
        return null;
    }

    @Override
    public String checkCase() {
        List<String> listParamRequest = new ArrayList<>(Arrays.asList(
                request.getParameter("to"),
                request.getParameter("from"),
                request.getParameter("offset"),
                request.getParameter("limit")
        ));
        for(String error: listParamRequest){
            if(error == null)
                return "Некорректная передача параметров в запросе.";
        }
        return null;
    }

    @Override
    public String checkAll() throws ParseException {
        if(this.checkCase()!=null)
            return this.checkCase();
        if (this.checkDateInput() != null)
            return this.checkDateInput();
        else if (this.checkDateOrder() != null)
            return this.checkDateOrder();
        else if (this.checkOffsetAndLimit() != null)
            return this.checkOffsetAndLimit();
        return null;
    }


}
