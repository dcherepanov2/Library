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
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//https://ru.stackoverflow.com/questions/991083/oauth2-spring-%D0%90%D0%B2%D1%82%D0%BE%D1%80%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F-%D1%87%D0%B5%D1%80%D0%B5%D0%B7-vk-%D0%BE%D1%88%D0%B8%D0%B1%D0%BA%D0%B0-invalid-token-response-tokentype-ca
@Configuration
@ComponentScan("com.example.MyBookShopApp")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private static String CLIENT_PROPERTY_KEY
//            = "spring.security.oauth2.client.registration.";
    private static final String LOGIN_ENDPOINT = "/login";

    private static final String REQUEST_CONTACT_CONFIRMATION_ENDPOINT = "/requestContactConfirmation";

    private static final String APPROVE_CONTACT_ENDPOINT = "/approveContact";
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityExceptionResolver securityExceptionResolver;
    private final AccessDeniedHandlerJwt accessDeniedHandlerJwt;
    private final JwtUserDetailsService customUserDetailsService;
    private final Environment env;

    private static List<String> clients = Arrays.asList("google");

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, SecurityExceptionResolver securityExceptionResolver, AccessDeniedHandlerJwt accessDeniedHandlerJwt, JwtUserDetailsService customUserDetailsService, Environment env) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.securityExceptionResolver = securityExceptionResolver;
        this.accessDeniedHandlerJwt = accessDeniedHandlerJwt;
        this.customUserDetailsService = customUserDetailsService;
        this.env = env;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    private ClientRegistration getRegistration(String client) {
//        String clientId = env.getProperty(
//                CLIENT_PROPERTY_KEY + client + ".client-id");
//
//        if (clientId == null) {
//            return null;
//        }
//
//        String clientSecret = env.getProperty(
//                CLIENT_PROPERTY_KEY + client + ".client-secret");
//
//        if (client.equals("google")) {
//            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
//                    .clientId(clientId).clientSecret(clientSecret).build();
//        }
//        return null;
//    }

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
                .antMatchers(REQUEST_CONTACT_CONFIRMATION_ENDPOINT).permitAll()
                .antMatchers(APPROVE_CONTACT_ENDPOINT).permitAll()
                .antMatchers("/books/changeBookStatus/**").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers("**/img/save").hasRole("ADMIN")
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

//    @Bean
//    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
//        return new HttpSessionOAuth2AuthorizationRequestRepository();
//    }

//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        List<ClientRegistration> registrations = clients.stream()
//                .map(this::getRegistration)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//        return new InMemoryClientRegistrationRepository(registrations);
//    }

//    @Bean
//    public OAuth2AuthorizedClientService authorizedClientService() {
//        return new InMemoryOAuth2AuthorizedClientService(
//                clientRegistrationRepository());
//    }
}
