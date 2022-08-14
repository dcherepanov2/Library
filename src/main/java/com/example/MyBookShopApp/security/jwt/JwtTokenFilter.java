package com.example.MyBookShopApp.security.jwt;

import com.example.MyBookShopApp.data.user.JwtLogoutToken;
import com.example.MyBookShopApp.exception.SecurityExceptionResolver;
import com.example.MyBookShopApp.repo.userrepos.JwtBlacklistRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;
    private SecurityExceptionResolver securityExceptionResolver = new SecurityExceptionResolver();
    private org.springframework.web.context.support.SpringBeanAutowiringSupport SpringBeanAutowiringSupport;
    private JwtBlacklistRepo jwtBlacklistRepo;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
            if (jwtBlacklistRepo == null) {
                ServletContext servletContext = req.getServletContext();
                WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                jwtBlacklistRepo = webApplicationContext.getBean(JwtBlacklistRepo.class);
            }
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
            JwtLogoutToken jwtLogoutToken = jwtBlacklistRepo.findByName("Bearer_"+token);
            if(jwtLogoutToken != null){
                securityExceptionResolver.commence((HttpServletRequest) req,
                                                   (HttpServletResponse) res,
                                                    new JwtAuthenticationException("Your token has been blocked or logout, please login again"));
            }
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                else {
                    securityExceptionResolver.commence((HttpServletRequest) req,
                                                       (HttpServletResponse) res,
                                                       new UsernameNotFoundException("Username not found in the system"));
                }
            }
            filterChain.doFilter(req, res);
    }


}
