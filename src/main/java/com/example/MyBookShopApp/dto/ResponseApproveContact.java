package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.enums.ErrorCodeResponseApproveContact;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseApproveContact extends ResultTrue{
    private Boolean returnResponse = true;
    private String error = ErrorCodeResponseApproveContact.SUCCESS.getMessage();

    public ResponseApproveContact(Boolean returnResponse, String error) {
        this.returnResponse = returnResponse;
        this.error = error;
    }

    @Override
    public Boolean getResult() {
        return super.getResult();
    }

    @Override
    public void setResult(Boolean result) {
        super.setResult(result);
    }

}
