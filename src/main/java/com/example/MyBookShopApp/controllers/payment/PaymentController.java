package com.example.MyBookShopApp.controllers.payment;

import com.example.MyBookShopApp.dto.PaymentRequestDto;
import com.example.MyBookShopApp.exception.PaymentDebitException;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.payment.RobokassaPaymentService;
import com.example.MyBookShopApp.service.payment.TransactionService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.NoSuchAlgorithmException;

@Controller
public class PaymentController {

    private final RobokassaPaymentService robokassa;
    @Qualifier("transactionServiceImpl")
    private final TransactionService transactionService;

    private final UserServiceImpl userService;

    @Autowired
    public PaymentController(RobokassaPaymentService robokassa, TransactionService transactionService, UserServiceImpl userService) {
        this.robokassa = robokassa;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @PostMapping(
            path = "/payment",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public RedirectView payment(JwtUser user, PaymentRequestDto paymentRequestDto) throws NoSuchAlgorithmException, PaymentDebitException {
        // так как у нас нету активного хоста, на который мы можем реализовать переадресацию и на маппинге
        // сделать подтверждение или отказ по платежу, то тогда
        // я думаю, что хорошим вариантом будет считать, что каждый платеж пользователя успешен(затычка)
        transactionService.debitPayment(user, paymentRequestDto.getSum());
        user.setBalance(user.getBalance() + paymentRequestDto.getSum());
        userService.setBalance(user, paymentRequestDto.getSum());
        if (paymentRequestDto.getSum() > 0)
            user.setBalance(user.getBalance() + paymentRequestDto.getSum());
        else
            throw new PaymentDebitException("Debit sum must be more zero.");
        return new RedirectView(robokassa.getPaymentUrl(paymentRequestDto.getSum()));
    }
}
