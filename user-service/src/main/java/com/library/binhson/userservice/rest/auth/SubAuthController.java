package com.library.binhson.userservice.rest.auth;

import com.library.binhson.userservice.dto.BaseResponse;
import com.library.binhson.userservice.dto.ForgotPasswordRequest;
import com.library.binhson.userservice.dto.ResetPasswordRequest;
import com.library.binhson.userservice.rest.BaseController;
import com.library.binhson.userservice.service.IAuthService;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SubAuthController implements BaseController {
    private final IAuthService authService;
    @PostMapping("/forgot-password")
     ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        try {
            authService.forgotPassword(forgotPasswordRequest);
        }catch (NoClassDefFoundError ex){
            ex.printStackTrace();
        }
        return ResponseEntity.ok().body(BaseResponse.builder().message("Resetting your password information was sent to your email.").build());
    }
    @Secured("authenticated")
    @PostMapping("/reset-password")
     ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        authService.resetPasswrod(resetPasswordRequest);
        return ResponseEntity.ok().body(BaseResponse.builder().message("Reset password: Successfully").build());
    }
}
