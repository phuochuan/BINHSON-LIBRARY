package com.library.binhson.userservice.security;

import com.library.binhson.userservice.entity.Account;
import com.library.binhson.userservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account=accountRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Email don't exist in app."));
        return CustomUserDetails.builder().account(account).build();
    }
}
