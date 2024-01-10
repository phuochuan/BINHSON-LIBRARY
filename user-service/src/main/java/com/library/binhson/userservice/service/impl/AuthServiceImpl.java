package com.library.binhson.userservice.service.impl;

import com.library.binhson.userservice.dto.*;
import com.library.binhson.userservice.entity.ConfirmToken;
import com.library.binhson.userservice.entity.User;
import com.library.binhson.userservice.repository.ConfirmTokenRepository;
import com.library.binhson.userservice.repository.UserRepository;
import com.library.binhson.userservice.service.IAuthService;
import com.library.binhson.userservice.service.IEmailService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.Config;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor

public class AuthServiceImpl implements IAuthService {
    private final RealmResource realmResource;
    private final IEmailService emailService;
    private final UserRepository userRepository;
    private final ConfirmTokenRepository tokenRepository;
    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;
    @Value("${keycloak.client_id}")
    private String clientId;
    @Value("${keycloak.grant_type}")
    private String granType;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
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
        return LoginResponse.builder()
                .accessToken(responseMap.get("access_token"))
                .refreshToken(responseMap.get("refresh_token"))
                .build();

    }

    @Override
    public BaseResponse signUp(RegistrationRequest registrationRequest) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(registrationRequest.username());
        user.setEmail(registrationRequest.email());
        user.setEnabled(true);
        user.setFirstName(registrationRequest.firstName());
        user.setLastName(registrationRequest.lastName());
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType("Password");
        credentialRepresentation.setValue(registrationRequest.password());
        user.setCredentials(Arrays.asList(credentialRepresentation));
        var response = realmResource.users().create(user);
        String userId = getUserId(response);
        if (Objects.nonNull(userId)) {
            RoleRepresentation roleRepresentation = realmResource.roles().get("ROLE_USER").toRepresentation();
            realmResource.users().get(userId).roles().realmLevel().add(Arrays.asList(roleRepresentation));
        }
        User myDBUser= User.builder()
                .id(userId)
                .isNonClocked(true)
                .build();
        userRepository.save(myDBUser);
        return BaseResponse.builder().message("Registration is successful. ").build();
    }

    private String getUserId(Response response) {

        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            String userUrl = response.getLocation().toString();
            String userId = userUrl.substring(userUrl.lastIndexOf("/") + 1);
            return userId;
        }
        return null;
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
        try {
            emailService.sendMailToForgotPassword(userRepresentation.getUsername(), userRepresentation.getEmail(), confirmCode);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private boolean validResetPasswordRequest(ResetPasswordRequest resetPasswordRequest) {
        return Objects.isNull(resetPasswordRequest.newPassword())
                || Objects.isNull(resetPasswordRequest.oldPassword());
    }



}
