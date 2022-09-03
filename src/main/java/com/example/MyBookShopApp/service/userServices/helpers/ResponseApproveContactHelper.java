package com.example.MyBookShopApp.service.userServices.helpers;

import com.example.MyBookShopApp.dto.ResponseApproveContact;
import com.example.MyBookShopApp.enums.ErrorCodeResponseApproveContact;
import org.springframework.stereotype.Service;

@Service
public class ResponseApproveContactHelper
{
    private ResponseApproveContact setFalseResultAndReturnResponse(){
        ResponseApproveContact responseApproveContact = new ResponseApproveContact();
        responseApproveContact.setResult(false);
        responseApproveContact.setReturnResponse(false);
        return responseApproveContact;
    }
    public ResponseApproveContact createOldCodeObject(){
        ResponseApproveContact responseApproveContact = this.setFalseResultAndReturnResponse();
        responseApproveContact.setError(ErrorCodeResponseApproveContact.OLD_CODE.getMessage());
        return responseApproveContact;
    }
    public ResponseApproveContact createIncorrectErrorCodeObject(){
        ResponseApproveContact responseApproveContact = this.setFalseResultAndReturnResponse();
        responseApproveContact.setError(ErrorCodeResponseApproveContact.INCORRECT_ERROR_CODE.getMessage());
        return responseApproveContact;
    }
    public ResponseApproveContact createNumberOfInputAttemptSObject(){
        ResponseApproveContact responseApproveContact = this.setFalseResultAndReturnResponse();
        responseApproveContact.setError(ErrorCodeResponseApproveContact.NUMBER_OF_INPUT_ATTEMPTS.getMessage());
        return responseApproveContact;
    }
}
