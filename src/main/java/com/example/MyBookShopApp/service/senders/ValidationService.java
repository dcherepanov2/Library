package com.example.MyBookShopApp.service.senders;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
        return errors.toString();
    }
}
