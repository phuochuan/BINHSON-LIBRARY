package com.library.binhson.paymentservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static String getUsername(){
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
