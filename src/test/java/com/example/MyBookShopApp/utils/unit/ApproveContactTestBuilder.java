package com.example.MyBookShopApp.utils.unit;

import com.example.MyBookShopApp.dto.ApproveContactDto;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApproveContactTestBuilder {

    private final ApproveContactDto approveContactDto;

    public ApproveContactTestBuilder() {
        this.approveContactDto = new ApproveContactDto();
    }

    public ApproveContactTestBuilder setCode(){
        approveContactDto.setCode("123123");
        return this;
    }

    public ApproveContactTestBuilder setContact(){
        approveContactDto.setContact("danilcherep7@gmail.com");
        return this;
    }

    public ApproveContactDto build(){
        return approveContactDto;
    }
}
