package com.example.MyBookShopApp.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class CookieUtils {

    private CookieUtils(){

    }
    public static Cookie deleteSlugFromCookies(HttpServletRequest httpServletRequest, String slug, String cookieName) {
        Cookie cookie = Arrays.stream(httpServletRequest.getCookies())
                .filter(x -> x.getName().equals(cookieName))
                .findFirst().orElse(null);
        if (cookie != null) {
            if (cookie.getValue().contains("/") && cookie.getValue().contains(slug)) {
                StringBuilder removeSlug = new StringBuilder(cookie.getValue());
                removeSlug.delete(removeSlug.toString().indexOf(slug), removeSlug.indexOf(slug) + slug.length() + 1);
                cookie.setValue(removeSlug.toString());
            } else if (cookie.getValue().contains(slug)) {
                cookie.setValue("");
                cookie.setMaxAge(0);
            }
            cookie.setPath("/");
        }
        return cookie;
    }
}
