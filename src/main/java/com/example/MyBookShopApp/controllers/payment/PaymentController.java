package com.example.MyBookShopApp.controllers.payment;

import com.example.MyBookShopApp.dto.CartPostponedCounterDto;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.ws.rs.BadRequestException;
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

    @ModelAttribute("countPostponed")
    public CartPostponedCounterDto cartPostponedCounterDto(@CookieValue(name = "cartContents", required = false) String cartContents,
                                                           @CookieValue(name = "keptContents", required = false) String keptContents) {
        CartPostponedCounterDto cartPostponedCounterDto = new CartPostponedCounterDto();
        if (cartContents != null && !cartContents.equals(""))
            cartPostponedCounterDto.setCountCart(cartContents.split("/").length);
        if (keptContents != null && !keptContents.equals(""))
            cartPostponedCounterDto.setCountPostponed(keptContents.split("/").length);
        return cartPostponedCounterDto;
    }

    @PostMapping(
            path = "/payment",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    public RedirectView payment(JwtUser user, PaymentRequestDto paymentRequestDto) throws NoSuchAlgorithmException, PaymentDebitException {
        // так как у нас нету активного хоста, на который мы можем реализовать переадресацию и на маппинге
        // сделать подтверждение или отказ по платежу, то тогда
        // я думаю, что хорошим вариантом будет считать, что каждый платеж пользователя успешен(затычка)
        if (paymentRequestDto != null && paymentRequestDto.getSum() != null) {
            if (paymentRequestDto.getSum() > 100000000.00)
                throw new PaymentDebitException("Максимальное значение счета физического лица составляет 100000000 рублей.");
            transactionService.debitPayment(user, paymentRequestDto.getSum());
            user.setBalance(user.getBalance() + paymentRequestDto.getSum());
            userService.setBalance(user, paymentRequestDto.getSum());
            if (paymentRequestDto.getSum() > 0)
                user.setBalance(user.getBalance() + paymentRequestDto.getSum());
            else
                throw new PaymentDebitException("Debit sum must be more zero.");
            return new RedirectView(robokassa.getPaymentUrl(paymentRequestDto.getSum()));
        }
        throw new BadRequestException("Некорректный запрос");
    }

    @PostMapping("/profile/save")
    public void saveNewInfoProfile() {//TODO: доделать

    }
}
