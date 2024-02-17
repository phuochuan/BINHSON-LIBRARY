package com.library.binhson.userservice.service.impl;

import com.library.binhson.userservice.dto.*;
import com.library.binhson.userservice.entity.ConfirmToken;
import com.library.binhson.userservice.entity.Role;
import com.library.binhson.userservice.entity.User;
import com.library.binhson.userservice.repository.ConfirmTokenRepository;
import com.library.binhson.userservice.repository.UserRepository;
import com.library.binhson.userservice.service.IAuthService;
import com.library.binhson.userservice.service.IEmailService;
import com.library.binhson.userservice.service.third_party_system.KeycloakService;
import com.library.binhson.userservice.ultils.ValidAuthUtil;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor

public class AuthServiceImpl implements IAuthService {
    private final RealmResource realmResource;
    private final IEmailService emailService;
    private final UserRepository userRepository;
    private final ConfirmTokenRepository confirmTokenRepository;
    private final KeycloakService keycloakService;
    private final PasswordEncoder passwordEncoder;
    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;
    @Value("${keycloak.client_id}")
    private String clientId;
    @Value("${keycloak.grant_type}")
    private String granType;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String authUrl = keycloakUrl + "token";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", granType);
        map.add("client_id", clientId);
        map.add("username", loginRequest.username());
        map.add("password", loginRequest.password());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, request, Map.class);
        Map<String, String> responseMap = response.getBody();
        return AuthResponse.builder()
                .accessToken(responseMap.get("access_token"))
                .refreshToken(responseMap.get("refresh_token"))
                .build();

    }

    @Override
    public BaseResponse signUp(RegistrationRequest registrationRequest) {
        if (!ValidAuthUtil.validRegistrationRequest(registrationRequest))
            throw new BadRequestException();
        if (userRepository.existsByEmail(registrationRequest.email().trim()))
            throw new BadRequestException("Email existed on a other account.");
        String userId = null;
        try {
            userId = keycloakService.registerUser(registrationRequest, false);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServerErrorException(500);
        }
        if (Objects.nonNull(userId)) {
            User myDBUser = User.builder()
                    .id(userId)
                    .dateOfAccountSignUp(new Date())
                    .isNonLocked(true)
                    .lastname(registrationRequest.lastName())
                    .firstname(registrationRequest.firstName())
                    .email(registrationRequest.email())
                    .dateOfBirth(registrationRequest.dateOfBirth())
                    .password(passwordEncoder.encode(registrationRequest.password()))
                    .build();
            userRepository.save(myDBUser);
            keycloakService.setRole(userId, "ROLE_"+ Role.MEMBER);
        }
        return BaseResponse.builder().message("Registration is successful. ").build();

    }


    @Override
    public void resetPasswrod(ResetPasswordRequest resetPasswordRequest) {
        if (validResetPasswordRequest(resetPasswordRequest)) {
            log.error(resetPasswordRequest.toString());
            throw new IllegalArgumentException("Fail to reset password:  The new password and old password must not null.");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserRepresentation userRepresentation = realmResource.users()
                .searchByUsername(username, true)
                .stream()
                .findFirst()
                .get();
        UserResource userResource = realmResource.users().get(userRepresentation.getId());
        Boolean isTrueOldPassword = realmResource.users().get(userRepresentation.getId()).credentials()
                .get(0).equals(resetPasswordRequest.oldPassword());
        if (isTrueOldPassword) {
            throw new IllegalArgumentException("Fail to reset password:  The old password is incorrect.");
        }

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(resetPasswordRequest.newPassword());
        userResource.resetPassword(credentialRepresentation);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        UserRepresentation userRepresentation = realmResource.users()
                .searchByEmail(forgotPasswordRequest.email(), true).get(0);
        if (Objects.isNull(userRepresentation))
            throw new BadRequestException("Email yet have been signed up before.");
        var user = userRepository.findById(userRepresentation.getId()).orElseThrow(() -> new NotFoundException());
        String confirmCode = UUID.randomUUID().toString();
        var confirmToken = ConfirmToken.builder()
                .token(confirmCode)
                .generativeDate(new Date()).isNonExpired(true).user(user)
                .build();
        confirmTokenRepository.save(confirmToken);
        try {
            emailService.sendMailToForgotPassword(userRepresentation.getUsername(), userRepresentation.getEmail(), confirmCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        return keycloakService.refreshToken(refreshToken);
    }

    private boolean validResetPasswordRequest(ResetPasswordRequest resetPasswordRequest) {
        return Objects.isNull(resetPasswordRequest.newPassword())
                || Objects.isNull(resetPasswordRequest.oldPassword());
    }


}
