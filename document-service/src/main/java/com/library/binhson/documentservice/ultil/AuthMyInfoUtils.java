package com.library.binhson.documentservice.ultil;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;

public class AuthMyInfoUtils {
    public static String getUsername(){
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName().toString();
        return username;
    }

    public static Collection<? extends  GrantedAuthority>  getAuthorities(){
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities();
    }

    public static  boolean isAdminOrLibrarianPermission(){
        return getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_ADMIN")
                        || grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_LIBRARIAN")
                );
    }


}
