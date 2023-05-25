package com.example.MyBookShopApp.service.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class RobokassaPaymentService implements PaymentService{

    @Value("${robokassa.test.merchant}")
    private String merchantLogin;

    @Value("${robokassa.test.password.first}")
    private String firstTestPassword;

    @Override
    public String getPaymentUrl(Double sum) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        String invId = "52341123";
        messageDigest.update((merchantLogin+":"+sum+":"+invId+":"+firstTestPassword).getBytes());
        return "https://auth.robokassa.ru/Merchant/Index.aspx"+
                "?MerchantLogin="+ merchantLogin+
                "&InvId="+invId+
                "&Culture=ru"+
                "&Encode=utf-8"+
                "&OutSum="+sum+
                "&SignatureValue="+ DatatypeConverter.printHexBinary(messageDigest.digest())+
                "&IsTest=1";
    }
}
