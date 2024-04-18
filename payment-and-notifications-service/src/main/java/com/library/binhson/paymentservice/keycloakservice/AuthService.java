package com.library.binhson.paymentservice.keycloakservice;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.AccessToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtDecoder jwtDecoder;

    public String verifyJwt(String jwtToken) {
        Jwt jwt=jwtDecoder.decode(jwtToken);
        return jwt.getSubject();
    }
}
