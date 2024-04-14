package com.library.binhson.userservice.service.third_party_system.keycloak;

import com.library.binhson.userservice.dto.AuthResponse;
import com.library.binhson.userservice.dto.ChangeEmail;
import com.library.binhson.userservice.dto.RegistrationRequest;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

@Service
@Slf4j
public class KeycloakService {
    protected final RealmResource realmResource;
    protected final Keycloak keycloak;
    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;
    @Value("${keycloak.client_id}")
    private String clientId;

    public KeycloakService(RealmResource realmResource, Keycloak keycloak) {
        this.realmResource = realmResource;
        this.keycloak = keycloak;
    }


    public String registerUser(RegistrationRequest registrationRequest,boolean isResetPassword) {
        UserRepresentation user = getUserRepresentation(registrationRequest,isResetPassword );
        var response = realmResource.users().create(user);
        return getUserId(response);
    }

    private static UserRepresentation getUserRepresentation(RegistrationRequest registrationRequest, boolean isResetPassword) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(registrationRequest.username());
        user.setEmail(registrationRequest.email());
        user.setEnabled(true);
        user.setFirstName(registrationRequest.firstName());
        user.setLastName(registrationRequest.lastName());
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType("Password");
        String password=registrationRequest.password();
        if(Objects.isNull(password) || password.trim().isEmpty() || isResetPassword)
            password= registrationRequest.email();
        credentialRepresentation.setValue(password);
        user.setCredentials(Arrays.asList(credentialRepresentation));
        return user;
    }
    private String getUserId(Response response) {
        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            String userUrl = response.getLocation().toString();
            String userId = userUrl.substring(userUrl.lastIndexOf("/") + 1);
            return userId;
        }
        log.error("ERROR"+response.getStatusInfo() +" "+ response.getHeaders() +" "+ response.getEntity().toString());
        throw new RuntimeException("Creating new account that is fail.");
    }

    public void setRole(String userId, String roleUser) {
        RoleRepresentation roleRepresentation = realmResource.roles().get(roleUser).toRepresentation();
        realmResource.users().get(userId).roles().realmLevel().add(Arrays.asList(roleRepresentation));
    }

    public void disableUser(String userId) {
        UsersResource userRessource = realmResource.users();
        UserRepresentation user = userRessource.get(userId).toRepresentation();
        user.setEnabled(false);
        userRessource.get(userId).update(user);
    }

    public String getIdByUsername(String username) {
        UsersResource userRessource = realmResource.users();
        UserRepresentation user = userRessource.searchByUsername(username,true).get(0);
        return user.getId();
    }
    public UserRepresentation getUserByUsername(String username){
        UsersResource userRessource = realmResource.users();
        UserRepresentation user = userRessource.searchByUsername(username,true).get(0);
        return user;
    }


    public void changeMail(ChangeEmail changeEmail, UserRepresentation keycloakUser) {
        keycloakUser.setEmail(changeEmail.newEmail());
        realmResource.users().get(keycloakUser.getId()).update(keycloakUser);
    }

    public String getPassword(String id) {
        var credentials=realmResource.users().get(id).credentials();
        return credentials.get(0).getValue();
    }

    public AuthResponse refreshToken(String refreshToken) {
        var refreshTokenUrl=keycloakServerUrl+"token";
        RestTemplate restTemplate= new RestTemplate();
        MultiValueMap<String, String> bodyValue= new LinkedMultiValueMap<>();
        bodyValue.add("client_id", clientId);
        bodyValue.add("grant_type", "refresh_token");
        bodyValue.add("refresh_token", refreshToken);
        bodyValue.add("client_secret","");

        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String,String>> mapHttpEntity=new HttpEntity<>(bodyValue, headers);
        ResponseEntity<HashMap> response=restTemplate.postForEntity(refreshTokenUrl,mapHttpEntity, HashMap.class);
        if(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().value()==200){
            log.info(""+ response.getBody().toString());
            var authResponse=AuthResponse.builder()
                    .accessToken((String) response.getBody().get("access_token"))
                    .refreshToken((String) response.getBody().get("refresh_token")).build();
            return authResponse;
        }else
            throw new RuntimeException("Server Error");
    }
}
