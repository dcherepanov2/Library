package org.example.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    private final Logger logger = Logger.getLogger(LoginService.class);
    static final private Map<String,String> loginPassword = new HashMap<>();

    public void add(LoginForm loginForm){
        for (Map.Entry<String, String> search : loginPassword.entrySet()){
            if(search.getKey().equals(loginForm.getUsername())&&search.getValue().equals(loginForm.getPassword())){
                logger.info("the user already exists in the system" + loginForm);
                return;
            }
        }
        loginPassword.put(loginForm.getUsername(),loginForm.getPassword());
    }

    public boolean authenticate(LoginForm loginFrom) {
        logger.info("try auth with user-form: " + loginFrom);
        for (Map.Entry<String, String> search : loginPassword.entrySet()){
            if(search.getKey().equals(loginFrom.getUsername())&&search.getValue().equals(loginFrom.getPassword())){
                logger.info("auth true");
                return true;
            }
        }
        return loginFrom.getUsername().equals("root") && loginFrom.getPassword().equals("123");//оставил ,чтобы после выполнения
        // пункта можно было входить и не запариваться )
    }
}
