package com.library.binhson.userservice.service;

import com.library.binhson.userservice.dto.*;

public interface IAuthService {
    AuthResponse login(LoginRequest loginRequest);

    BaseResponse signUp(RegistrationRequest registrationRequest);

    void resetPasswrod(ResetPasswordRequest resetPasswordRequest);

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    AuthResponse refreshToken(String s);

    AuthResponse getAccessToken(String authorizationCode);
}
