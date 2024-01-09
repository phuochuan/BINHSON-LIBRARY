package com.library.binhson.userservice.dto;

import lombok.Data;


public record ResetPasswordRequest(String oldPassword, String newPassword) {
}
