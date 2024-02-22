package com.library.binhson.userservice.rest;

import com.library.binhson.userservice.dto.AuthResponse;
import com.library.binhson.userservice.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-service/idp")
@RequiredArgsConstructor
public class AuthIdPController {
    private final IAuthService authService;
    @PostMapping("/access_token/github/{authorization_code}")
    ResponseEntity<?> getAccessToken(@PathVariable("authorization_code") String authorizationCode){
        AuthResponse authResponse=authService.getAccessToken(authorizationCode);
        return ResponseEntity.ok(authResponse);
    }
}
