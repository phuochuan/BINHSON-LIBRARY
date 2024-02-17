package com.library.binhson.userservice.rest;

import com.library.binhson.userservice.dto.*;
import com.library.binhson.userservice.service.IAuthService;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/user-service/auth")
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            var loginResponse = authService.login(loginRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (HttpClientErrorException.Unauthorized ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(401).body("{\"message\": \"" + ex.getMessage() + "\"}");
        }
    }

    @PostMapping("/registrations")
    @CacheEvict(value = "all_users", allEntries = true)
    ResponseEntity<?> registrations(@RequestBody RegistrationRequest registrationRequest) {
        var registrationResponse = authService.signUp(registrationRequest);
        return ResponseEntity.ok(registrationResponse);
    }


    @PostMapping("/forgot-password")
    ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        try {
            authService.forgotPassword(forgotPasswordRequest);
        } catch (NoClassDefFoundError ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.ok().body(BaseResponse.builder().message("Resetting your password information was sent to your email.").build());
    }

    @Secured("authenticated")
    @PostMapping("/reset-password")
    ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPasswrod(resetPasswordRequest);
        return ResponseEntity.ok().body(BaseResponse.builder().message("Reset password: Successfully").build());
    }

    @PostMapping("refresh-token")
    ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        if(Objects.isNull(refreshTokenRequest.refresh_token()))
            throw new BadRequestException("RefreshToken mustn't null. ");
        var authResponse=authService.refreshToken(refreshTokenRequest.refresh_token());
        return ResponseEntity.ok(authResponse);
    }

}
