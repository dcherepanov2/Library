package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = Logger.getLogger(AppSecurityConfig.class.getName());

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("populate inmemory auth user");
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("1"))
                .roles("USER");
    }

    @Override
    public void configure(WebSecurity web){
        web
                .ignoring()
                .antMatchers("/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/auth")
                .defaultSuccessUrl("/books/shelf",true)
                .failureUrl("/login");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

