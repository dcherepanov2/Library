package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.enums.ErrorCodeResponseApproveContact;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseApproveContact extends ResultTrue implements AopDto{
    private Boolean returnResponse = true;
    private String error = ErrorCodeResponseApproveContact.SUCCESS.getMessage();

    public ResponseApproveContact(Boolean returnResponse, String error) {
        this.returnResponse = returnResponse;
        this.error = error;
    }

}
