package com.library.binhson.userservice.service.third_party_system;

import com.library.binhson.userservice.dto.AuthResponse;
import com.library.binhson.userservice.dto.ChangeEmail;
import com.library.binhson.userservice.dto.RegistrationRequest;
import com.library.binhson.userservice.repository.UserRepository;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class KeycloakService {
    private final RealmResource realmResource;
    private final Keycloak keycloak;


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
        return null;
    }
}
