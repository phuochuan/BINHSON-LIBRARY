package com.library.binhson.borrowingservice.utils;

import com.library.binhson.borrowingservice.entity.Librarian;
import com.library.binhson.borrowingservice.entity.Person;
import com.library.binhson.borrowingservice.repository.LibrarianRepository;
import com.library.binhson.borrowingservice.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
public class AuthUtils {

    public static String getUsername(){
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName().toString();
        return username;
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(){
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
