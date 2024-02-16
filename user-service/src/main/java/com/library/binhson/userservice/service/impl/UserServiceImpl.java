package com.library.binhson.userservice.service.impl;

import com.library.binhson.userservice.dto.*;
import com.library.binhson.userservice.entity.User;
import com.library.binhson.userservice.repository.UserRepository;
import com.library.binhson.userservice.service.IUserService;
import com.library.binhson.userservice.service.third_party_system.KeycloakService;
import com.library.binhson.userservice.ultils.ValidAuthUtil;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final KeycloakService keycloakService;
    @Override
    public List<UserDto> getAll() {
        List<User> users = get();
        log.info(users.size() + "");
        log.info(users.get(0).getId());
        return users.stream().map(user -> {
                    UserDto finalUser = modelMapper.map(user, UserDto.class);
                    finalUser.setIdentityLibraryCode(user.getId());
                    return finalUser;
                })
                .collect(Collectors.toList());
    }
    @Cacheable(value = "all_users")
    private List<User> get() {
        return userRepository.findAll();
    }

    @Override
    public UserDto createUser(AccountRC registrationRequest) {
        if(!ValidAuthUtil.validRegistrationRequestForUsers(registrationRequest))
            throw new BadRequestException();
        if(userRepository.existsByEmail(registrationRequest.email()))
            throw new BadRequestException("Email was applied to another account before.");
        String userId= keycloakService.registerUser(modelMapper.map(registrationRequest, RegistrationRequest.class),true);
        User myDBUser= User.builder()
                .id(userId)
                .dateOfAccountSignUp(new Date())
                .isNonLocked(true)
                .lastname(registrationRequest.lastName())
                .firstname(registrationRequest.firstName())
                .email(registrationRequest.email())
                .build();
        var saveUser=userRepository.save(myDBUser);
        keycloakService.setRole(userId, "ROLE_" + registrationRequest.role().getRoleName());
        var finalUser=modelMapper.map(saveUser, UserDto.class);
        finalUser.setIdentityLibraryCode(saveUser.getId());
        return finalUser;
    }

    @Override
    public void disableUser(String id) {
        keycloakService.disableUser( id);
        var user=userRepository.findById(id).orElseThrow();
        user.setIsNonLocked(false);
        userRepository.save(user);
    }

    @Override
    public UserDto update(String userId, UpdateProfileRequest updateProfile) {
        var user=userRepository.findById(userId).orElseThrow(()->new BadRequestException("No user have Id that is: "+userId));
        if(Objects.nonNull(updateProfile.address()))
            user.setAddress(updateProfile.address());
        if (Objects.nonNull(updateProfile.biography()))
            user.setBiography(updateProfile.biography());
        if(Objects.nonNull(updateProfile.dateOfBirth()))
            user.setDateOfBirth(updateProfile.dateOfBirth());
        if(Objects.nonNull(updateProfile.fistName()) && Objects.isNull(updateProfile.lastName()))
        {
            user.setFirstname(updateProfile.fistName());
            user.setLastname(updateProfile.lastName());
        }
        user=userRepository.save(user);
        var finalUser=modelMapper.map(user, UserDto.class);
        finalUser.setIdentityLibraryCode(userId);
        return finalUser;
    }

    @Override
    public List<UserDto> getDetailsAll() {
        List<User> users = get();
        return users.stream().map(user -> {
                    UserDto finalUser = modelMapper.map(user, UserDto.class);
                    finalUser.setIdentityLibraryCode(user.getId());
                    finalUser.setNoDotCitizenIdentityCardId(user.getCitizenIdentityCard().getNoDot());
                    finalUser.setPlaceOfOrigin(user.getCitizenIdentityCard().getPlaceOfOrigin());
                    finalUser.setPlaceOfResidence(user.getCitizenIdentityCard().getPlaceOfResidence());
                    finalUser.setPersonalIdentification(user.getCitizenIdentityCard().getPersonalIdentification());
                    return finalUser;
                })
                .collect(Collectors.toList());
    }
}
