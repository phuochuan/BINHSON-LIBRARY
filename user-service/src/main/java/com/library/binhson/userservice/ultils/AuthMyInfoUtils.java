package com.library.binhson.userservice.ultils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthMyInfoUtils {
    public static String getUsername(){
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName().toString();
        return username;
    }
}
