package com.library.binhson.userservice.config.security;

import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@Order(0)
class KeycloakRealmRolesGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public List<GrantedAuthority> convert(Jwt jwt) {
        final var realmAccess = (Map<String, Object>) jwt.getClaims().getOrDefault("realm_access", Map.of());
        final var realmRoles = (List<String>) realmAccess.getOrDefault("roles", List.of());
        return realmRoles.stream().map(SimpleGrantedAuthority::new).map(GrantedAuthority.class::cast).toList();
    }

    // phương thức convert để chuyển đổi thông tin từ đối tượng Jwt sang danh sách các GrantedAuthority.
    // Trong trường hợp này, nó đọc các vai trò (roles) từ claims của Jwt và chuyển đổi chúng thành danh sách
    // các GrantedAuthority, sử dụng SimpleGrantedAuthority để tạo các đối tượng được phân quyền. Điều này thường
    // được sử dụng để xác định quyền truy cập của người dùng dựa trên thông tin từ JWT được sinh ra bởi Keycloak
}