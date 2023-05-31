package com.example.MyBookShopApp.service.userServices.helpers;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.exception.UserHelperException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class UserHelper {
    private static final Random random;

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new UserHelperException(e.getMessage());
        }
    }


    public String generateCode() {
        return String.valueOf(random.nextInt(899999) + 100000);
    }

    public String getRedirectionEndpoint(String history, String... removeEndpoints) {
        List<String> splits = new ArrayList<>(Arrays.asList(history.split("/")));
        for (String removeEndpoint : removeEndpoints) {
            splits.removeIf(removeEndpoint::equals);
        }
        splits.replaceAll(x -> x.equals("main") ? "" : x);
        return splits.get(splits.size() - 1);
    }

    public String generateHash(User user) {
        String hash = RandomStringUtils.random(50, true, true) + user.hashCode();
        return hash.length() > 255 ? hash.substring(0, 255) : hash;
    }
}
