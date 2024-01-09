package com.library.binhson.userservice.rest.auth;

import com.library.binhson.userservice.dto.LoginRequest;
import com.library.binhson.userservice.dto.RegistrationRequest;
import com.library.binhson.userservice.rest.BaseController;
import com.library.binhson.userservice.service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainAuthController implements BaseController {
    private final IAuthService authService;
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            var loginResponse = authService.login(loginRequest);
            return ResponseEntity.ok(loginResponse);
        }catch (HttpClientErrorException.Unauthorized ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(401).body("{\"message\": \""+ex.getMessage()+"\"}");
        }
    }

    @PostMapping("/registrations")
    ResponseEntity<?> registrations(@RequestBody RegistrationRequest registrationRequest){
        var registrationResponse= authService.signUp(registrationRequest);
        return ResponseEntity.ok(registrationResponse);
    }
}
