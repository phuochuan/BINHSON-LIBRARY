package com.library.binhson.userservice.service.third_party_system.github;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Lazy
@Slf4j
public class GithubService {
    private final String githubTokenUrl = "https://github.com/login/oauth/access_token";
    @Value("${spring.github.id}")
    private String clientId;
    @Value("${spring.github.secret}")
    private String secret;
    @Value("${spring.github.redirect-url}")
    private String redirectUrl;

    private final String githubUserApiUrl = "https://api.github.com/user";
    public HashMap<String, String> getCredentials(String authorizationCode) {
        Map<String, String> accessGithubMap=getAGithubccessToken(authorizationCode);
        return getGithubCredentials(accessGithubMap);

    }

    private HashMap<String, String> getGithubCredentials(Map<String, String> accessGithubMap) {
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        String accessToken=accessGithubMap.get("access_token");
        if(Objects.isNull(accessToken))
            throw new BadRequestException("");
        headers.set("Authorization", "Bearer "+accessGithubMap.get("access_token"));
        headers.set("Accept", "application/vnd.github+json");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Map> response= restTemplate.exchange(
                githubUserApiUrl,
                HttpMethod.GET,
                requestEntity,
                Map.class);
        return (HashMap<String, String>) response.getBody();
    }

    private Map<String, String> getAGithubccessToken(String authorizationCode) {
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", secret);
        body.add("redirect_uri", redirectUrl);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response= restTemplate.postForEntity(githubTokenUrl,requestEntity, Map.class);
        return response.getBody();
    }
}
