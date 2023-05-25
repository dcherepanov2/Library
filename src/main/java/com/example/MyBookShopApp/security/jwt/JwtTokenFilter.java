package com.example.MyBookShopApp.security.jwt;

import com.example.MyBookShopApp.data.user.JwtLogoutToken;
import com.example.MyBookShopApp.exception.SecurityExceptionResolver;
import com.example.MyBookShopApp.repo.userrepos.JwtBlacklistRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;


public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityExceptionResolver securityExceptionResolver = new SecurityExceptionResolver();
    private JwtBlacklistRepo jwtBlacklistRepo;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        if (req.getCookies() == null) {
            filterChain.doFilter(req, res);
            return;
        }
        Optional<Cookie> cookie = Arrays.stream(req.getCookies()).filter(x -> x.getName().equals("token")).findFirst();
        if (cookie.isPresent()) {
            if (jwtBlacklistRepo == null) {
                ServletContext servletContext = req.getServletContext();
                WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                if (webApplicationContext != null) {
                    jwtBlacklistRepo = webApplicationContext.getBean(JwtBlacklistRepo.class);
                }
            }
            String token = jwtTokenProvider.resolveToken(req);
            if(jwtBlacklistRepo != null){
                JwtLogoutToken jwtLogoutToken = jwtBlacklistRepo.findByName("Bearer_" + token);
                if (jwtLogoutToken != null) {
                    securityExceptionResolver.commence(req,
                            res,
                            new JwtAuthenticationException("Your token has been blocked or logout, please login again"));
                }
            }

            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    securityExceptionResolver.commence(req, res,
                            new UsernameNotFoundException("Username not found in the system"));
                }
            }
        }
        filterChain.doFilter(req, res);
    }
}
