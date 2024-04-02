package com.library.binhson.userservice.service;

import com.library.binhson.userservice.dto.UserDto;

import java.util.List;

public interface IMemberService {
    List<UserDto> getMember(int i, int i1);

    Object findById(String memberId);

}
