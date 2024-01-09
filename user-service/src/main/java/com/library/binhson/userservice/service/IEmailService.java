package com.library.binhson.userservice.service;

public interface IEmailService {
    void sendMailToForgotPassword(String username, String email, String confirmCode);
}
