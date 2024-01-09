package com.library.binhson.userservice.service;

import com.library.binhson.userservice.dto.*;

public interface IAuthService {
    LoginResponse login(LoginRequest loginRequest);

    BaseResponse signUp(RegistrationRequest registrationRequest);

    void resetPasswrod(ResetPasswordRequest resetPasswordRequest);

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
}
