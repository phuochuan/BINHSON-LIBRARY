package com.library.binhson.userservice.ultils;

import com.library.binhson.userservice.dto.RegistrationRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;


public class ValidAuthUtil {
    public static Boolean validRegistrationRequest(RegistrationRequest registrationRequest){
        return Objects.nonNull(registrationRequest.email())
                && Objects.nonNull(registrationRequest.username())
                && Objects.nonNull(registrationRequest.password());
    }
    public static Boolean validRegistrationRequestForUsers(RegistrationRequest registrationRequest){
        return Objects.nonNull(registrationRequest.email())
                && Objects.nonNull(registrationRequest.username());
    }
}
