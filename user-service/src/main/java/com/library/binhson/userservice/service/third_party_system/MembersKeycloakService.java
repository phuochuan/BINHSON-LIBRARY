package com.library.binhson.userservice.service.third_party_system;

import com.library.binhson.userservice.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Lazy
@Slf4j
public class MembersKeycloakService extends KeycloakService{
    public MembersKeycloakService(RealmResource realmResource, Keycloak keycloak) {
        super(realmResource, keycloak);
    }

    public Boolean isMember(String userId){
            UsersResource userResource = super.realmResource.users();
            return userResource.get(userId).roles().realmLevel().listAll().stream().anyMatch(r->r.toString().equals("ROLE_"+Role.MEMBER.name()));

    }
    public Boolean isUser(String username){
        try {
            UsersResource userResource = super.realmResource.users();
            UserRepresentation user = userResource.searchByUsername(username, true).get(0);
//            return user.getRealmRoles().contains(Role.);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getMembers() {
        UsersResource userResource = super.realmResource.users();
        List<UserRepresentation> userRepresentations=super.realmResource.users().list().stream()
                .filter(user-> {
                    if(Objects.isNull(userResource.get(user.getId()).roles())){
                        log.info(""+user.getUsername());
                        log.info("Don't have role");
                        return false;
                    }
                    return userResource.get(user.getId()).roles().realmLevel().listAll()
                            .stream()
                            .anyMatch(r->r.toString().equals("ROLE_" + Role.MEMBER.name()));
                }).toList();
        log.info("SIZE: " +userRepresentations.size());
        return userRepresentations.stream().map(u->u.getId()).toList();
    }
}
