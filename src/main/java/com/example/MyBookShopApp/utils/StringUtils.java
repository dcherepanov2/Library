package com.example.MyBookShopApp.utils;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {

    private StringUtils(){

    }
    public static String deleteStringIntoString(String removeInto, String remove) {
        if (removeInto.contains(remove)) {
            StringBuilder sb = new StringBuilder(removeInto);
            int indexStart = removeInto.indexOf(remove);
            int indexEnd = removeInto.indexOf(remove) + remove.length();
            sb.delete(indexStart, indexEnd);
            return sb.toString();
        }
        return removeInto;
    }
}
