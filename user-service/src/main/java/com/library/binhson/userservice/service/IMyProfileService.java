package com.library.binhson.userservice.service;

import com.library.binhson.userservice.dto.ChangeEmail;
import com.library.binhson.userservice.dto.UpdateProfileRequest;
import com.library.binhson.userservice.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface IMyProfileService {
    UserDto getMyInfo();

    Object update(UpdateProfileRequest updateProfile);

    void setAvatar(MultipartFile avatarFile);

    void changeMail(ChangeEmail changeEmail);
}
