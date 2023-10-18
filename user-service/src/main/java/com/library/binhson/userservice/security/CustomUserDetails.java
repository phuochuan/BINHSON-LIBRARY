package com.library.binhson.userservice.security;

import com.library.binhson.userservice.entity.Account;
import com.library.binhson.userservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {
    private Account account;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return account.getAuthorization().getAuthority();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return account.getIsAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.getIsAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return account.getIsCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return account.getIsEnabled();
    }
}
