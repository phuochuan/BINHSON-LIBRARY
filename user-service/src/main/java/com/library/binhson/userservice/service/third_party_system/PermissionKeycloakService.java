package com.library.binhson.userservice.service.third_party_system;

import com.library.binhson.userservice.entity.Permission;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.stereotype.Service;

@Service
public class PermissionKeycloakService extends KeycloakService{
    public PermissionKeycloakService(RealmResource realmResource, Keycloak keycloak) {
        super(realmResource, keycloak);
    }

    public void allowBorrow(String userId) {
        super.setRole(userId, Permission.borrow.name());
    }
}
