package com.example.MyBookShopApp.configuration;


import com.example.MyBookShopApp.exception.CustomAuthenticationFailureHandler;
import com.example.MyBookShopApp.exception.SecurityExceptionResolver;
import com.example.MyBookShopApp.security.jwt.AccessDeniedHandlerJwt;
import com.example.MyBookShopApp.security.jwt.JwtConfigurer;
import com.example.MyBookShopApp.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@ComponentScan("com.example.MyBookShopApp")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String REG_ENDPOINT = "/api/v1/auth/registration";
    private static final String VERIFICATION_ENDPOINT = "/api/v1/auth/verification/**";
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityExceptionResolver securityExceptionResolver;
    private final AccessDeniedHandlerJwt accessDeniedHandlerJwt;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, SecurityExceptionResolver securityExceptionResolver, AccessDeniedHandlerJwt accessDeniedHandlerJwt) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.securityExceptionResolver = securityExceptionResolver;
        this.accessDeniedHandlerJwt = accessDeniedHandlerJwt;
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
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(REG_ENDPOINT).permitAll()
                .antMatchers(VERIFICATION_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}
