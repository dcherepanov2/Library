package com.example.MyBookShopApp.dto;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
//@Schema(description = "AuthenticationRequestDto Сущность предоставляющая пользователя в запросе, при аутентификации пользователя в системе")
@Component
public class AuthenticationRequestDto  implements AopDto{
//    @Schema(description = "Логин пользователя")
    @NotBlank(message = "Username should be between 6 and 20 character")
    @Size(min = 6, max = 20, message = "Username should be between 6 and 20 character")
    private String username;

//    @Schema(description = "Пароль пользователя")
    @NotBlank(message = "Password should ne not null")
    @Size(min = 8, max = 30, message = "Password should be between 6 and 20 character")
    private String password;

    @NotBlank(message = "Email should be not null")
    @Email(message = "Email should be format ***@***.**")
    private String email;

    @NotBlank(message = "Email should be not null")
    private String phone;
}
