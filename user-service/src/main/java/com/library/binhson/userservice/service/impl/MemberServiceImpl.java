package com.library.binhson.userservice.service.impl;

import com.library.binhson.userservice.dto.ObjectPage;
import com.library.binhson.userservice.dto.UserDto;
import com.library.binhson.userservice.dto.UserPageDto;
import com.library.binhson.userservice.service.IMemberService;
import com.library.binhson.userservice.service.IUserService;
import com.library.binhson.userservice.service.third_party_system.MembersKeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements IMemberService
{
    private final IUserService userService;
    private final MembersKeycloakService memberService;

    @Override
    public List<UserDto> getMember(int page, int size) {
        List<String> memberIds = memberService.getMembers();
        List<UserDto> userDtos=userService.getAll();
        var totalUser=userDtos.stream().filter(u->memberIds.contains(u.getIdentityLibraryCode())).collect(Collectors.toList());
        ObjectPage objectPage=new ObjectPage(totalUser, page, size);
        return (List<UserDto>) objectPage.getCurrentPage().stream().collect(Collectors.toList());
    }

    @Override
    public Object findById(String memberId) {
        List<UserDto> userDtos=userService.getAll();
        return userDtos.stream().filter(u->u.getIdentityLibraryCode().equals(memberId)).findFirst();
    }
}
