package com.library.binhson.userservice.service.impl;

import com.library.binhson.userservice.dto.ChangeEmail;
import com.library.binhson.userservice.dto.UpdateProfileRequest;
import com.library.binhson.userservice.dto.UserDto;
import com.library.binhson.userservice.entity.User;
import com.library.binhson.userservice.repository.UserRepository;
import com.library.binhson.userservice.service.IMyProfileService;
import com.library.binhson.userservice.service.third_party_system.KeycloakService;
import com.library.binhson.userservice.ultils.AuthMyInfoUtils;
import com.library.binhson.userservice.ultils.ValidationUtil;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class MyProfileServiceImpl implements IMyProfileService {
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getMyInfo() {

        var keycloakUser = getCurrentKeycloakUser();
        var user = userRepository.findById(keycloakUser.getId()).orElseThrow();
        if (Objects.nonNull(user)) {
            var resultUser = modelMapper.map(user, UserDto.class);
            resultUser.setUsername(keycloakUser.getUsername());
            resultUser.setIdentityLibraryCode(keycloakUser.getId());
            return resultUser;
        }
        throw new ServerErrorException(500);

    }

    private UserRepresentation getCurrentKeycloakUser() {
        String username = AuthMyInfoUtils.getUsername();
        var keycloakUser = keycloakService.getUserByUsername(username);
        return keycloakUser;
    }

    private User getCurrentUser() {
        String username = AuthMyInfoUtils.getUsername();
        var keycloakUser = keycloakService.getUserByUsername(username);
        var user = userRepository.findById(keycloakUser.getId()).orElseThrow();
        return user;
    }

    @Override
    public UserDto update(UpdateProfileRequest updateProfile) {
        var keycloakUser = getCurrentKeycloakUser();
        var user = userRepository.findById(keycloakUser.getId()).orElseThrow();
        if (Objects.nonNull(updateProfile.lastName) && Objects.nonNull(updateProfile.fistName)) {
            user.setLastname(updateProfile.lastName);
            user.setFirstname(updateProfile.fistName);
        }
        if (Objects.nonNull(updateProfile.dateOfBirth))
            user.setDateOfBirth(updateProfile.dateOfBirth);
        if (Objects.nonNull(updateProfile.biography))
            user.setBiography(updateProfile.biography);
        if (Objects.nonNull(updateProfile.address))
            user.setAddress(updateProfile.address);
        if (Objects.nonNull(updateProfile.phone) && ValidationUtil.isValidPhone(updateProfile.phone))
            user.setPhone(updateProfile.phone);
        user = userRepository.save(user);
        var resultUser = modelMapper.map(user, UserDto.class);
        resultUser.setUsername(keycloakUser.getUsername());
        resultUser.setIdentityLibraryCode(user.getId());
        return resultUser;
    }

    @Override
    public void setAvatar(MultipartFile avatarFile) {
        try {
            var user = getCurrentUser();
            user.setAvatar(avatarFile.getBytes());
            userRepository.save(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServerErrorException(500);
        }

    }

    @Override
    public void changeMail(ChangeEmail changeEmail) {
        var keycloakUser = getCurrentKeycloakUser();
        // var password=keycloakService.getPassword(keycloakUser.getId());//
        // As of 7/26/2021 getCredentials keeps always returning null.
        if (!userRepository.existsByEmail(changeEmail.newEmail())) {
            var user = userRepository.findById(keycloakUser.getId()).orElseThrow(() -> new ServerErrorException(500));
            var password = user.getPassword();
            if (passwordEncoder.matches(changeEmail.password(), password)) {
                keycloakService.changeMail(changeEmail, keycloakUser);
                user.setEmail(changeEmail.newEmail());
                userRepository.save(user);
            } else throw new BadRequestException("Password is incorrect!");
        } else {
            log.info(userRepository.existsByEmail(changeEmail.newEmail()) +"");
            throw new BadRequestException("Email existed!" );
        }

    }
}
