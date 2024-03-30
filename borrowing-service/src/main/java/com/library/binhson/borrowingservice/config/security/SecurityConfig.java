package com.library.binhson.borrowingservice.config.security;

import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    private final Converter<Jwt, Collection<GrantedAuthority>> authoritiesConverter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, ServerProperties serverProperties) throws Exception {

        // Configure a resource server with JWT decoder (the customized jwtAuthenticationConverter is picked by Spring Boot)
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));

        // State-less session (state in access-token only)
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Disable CSRF because of state-less session-management
        http.csrf(csrf -> csrf.disable());

        // Return 401 (unauthorized) instead of 302 (redirect to login) when
        // authorization is missing or invalid
        http.exceptionHandling(eh -> eh.authenticationEntryPoint((request, response, authException) -> {
            response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"Restricted Content\"");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }));

        // If SSL enabled, disable http (https only)
        if (serverProperties.getSsl() != null && serverProperties.getSsl().isEnabled()) {
            //Kiểm tra xem SSL có được kích hoạt trong cấu hình của ứng dụng không.

            http.requiresChannel(channel -> channel.anyRequest().requiresSecure());
            // phương thức này đặt yêu cầu cho tất cả các yêu cầu HTTP, yêu cầu chúng
            // phải sử dụng kênh an toàn (https). Điều này có ý nghĩa là nếu một yêu cầu đến
            // được gửi qua HTTP thay vì HTTPS, nó sẽ được chuyển hướng tự động sang HTTPS để đảm
            // bảo an toàn trong quá trình truyền tải dữ liệu.
        }

        // @formatter:off
        http.authorizeHttpRequests(requests -> requests
                        .anyRequest().permitAll())
                        .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(myConverter())
                        )
        );;
        // @formatter:on

        return http.build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> myConverter() {
        final var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        jwtAuthenticationConverter.setPrincipalClaimName(StandardClaimNames.PREFERRED_USERNAME);
        return jwtAuthenticationConverter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromOidcIssuerLocation("http://localhost:8442/realms/master");
    }
    /**
     * An authorities converter using solely realm_access.roles claim as source and doing no transformation (no prefix, case untouched)
     */



}
