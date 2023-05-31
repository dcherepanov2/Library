package com.example.MyBookShopApp.configuration;

import com.example.MyBookShopApp.exception.CustomAuthenticationFailureHandler;
import com.example.MyBookShopApp.exception.SecurityExceptionResolver;
import com.example.MyBookShopApp.security.JwtUserDetailsService;
import com.example.MyBookShopApp.security.jwt.AccessDeniedHandlerJwt;
import com.example.MyBookShopApp.security.jwt.JwtConfigurer;
import com.example.MyBookShopApp.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@ComponentScan("com.example.MyBookShopApp")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_ENDPOINT = "/login";

    private static final String REG_ENDPOINT = "/signup";

    private static final String REQUEST_CONTACT_CONFIRMATION_ENDPOINT = "/requestContactConfirmation";

    private static final String APPROVE_CONTACT_ENDPOINT = "/approveContact";

    private static final String ADMIN_ROLE_NAME = "ADMIN";
    private static final String USER_ROLE_NAME = "USER";

    private static final String MANAGER_ROLE_NAME = "MANAGER";
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityExceptionResolver securityExceptionResolver;
    private final AccessDeniedHandlerJwt accessDeniedHandlerJwt;
    private final JwtUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, SecurityExceptionResolver securityExceptionResolver, AccessDeniedHandlerJwt accessDeniedHandlerJwt, JwtUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.securityExceptionResolver = securityExceptionResolver;
        this.accessDeniedHandlerJwt = accessDeniedHandlerJwt;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .exceptionHandling().authenticationEntryPoint(securityExceptionResolver)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerJwt)
                .and()
                .authorizeRequests()
                .antMatchers("/books/changeBookStatus/**").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/user/logout").hasAnyRole(USER_ROLE_NAME, MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/books/comment/**").hasAnyRole(USER_ROLE_NAME, MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/rateBook").hasAnyRole(USER_ROLE_NAME, MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/rateBookReview").hasAnyRole(USER_ROLE_NAME, MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/books/pay").hasAnyRole(USER_ROLE_NAME, MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/download/**").hasAnyRole(USER_ROLE_NAME, MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/profile").hasAnyRole(USER_ROLE_NAME, MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/viewed/books").hasAnyRole(USER_ROLE_NAME, MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/books/**/img/save").hasAnyRole(ADMIN_ROLE_NAME, MANAGER_ROLE_NAME)
                .antMatchers("/cms/index").hasAnyRole(MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/cms/books/**").hasAnyRole(MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/cms/user/**").hasAnyRole(MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/cms/book-review/**").hasAnyRole(MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers("/cms/authors/**").hasAnyRole(MANAGER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(REG_ENDPOINT).permitAll()
                .antMatchers(REQUEST_CONTACT_CONFIRMATION_ENDPOINT).permitAll()
                .antMatchers(APPROVE_CONTACT_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        http.headers().frameOptions().disable();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
