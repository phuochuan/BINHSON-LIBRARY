package com.library.binhson.userservice.service;

import com.library.binhson.userservice.dto.*;

import java.util.List;

public interface IUserService {
    List<UserDto> getAll();

    UserDto createUser(AccountRC accountRC);

    void disableUser(String id);

    UserDto update(String userId, UpdateProfileRequest updateProfile);

    List<UserDto> getDetailsAll();
}
