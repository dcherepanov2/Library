package org.example.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class LoginForm {

    @NotEmpty()
    private String username;
    @NotEmpty()
    private String password;

    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
