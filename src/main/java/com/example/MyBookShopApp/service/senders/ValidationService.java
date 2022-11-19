package com.example.MyBookShopApp.service.senders;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.ws.rs.BadRequestException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationService {

    public String validate(BindingResult bindingResult){
        StringBuilder errors = new StringBuilder();
        if(bindingResult.hasErrors()){
            errors = new StringBuilder("Errors validation: ");
            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    errors.append(fieldError.getDefaultMessage()).append(", ");
                }
            }
            errors.append("Check your fields for correct entry");
        }
        else
            return "";
        throw new BadRequestException(errors.toString());
    }

    public boolean isPhone(String phoneNumber){
        phoneNumber = phoneNumber.replace("-","");
        String patterns
                = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean isEmail(String email){
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
