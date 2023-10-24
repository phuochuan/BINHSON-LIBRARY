package com.library.binhson.userservice.rest;

import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@Slf4j
public class demoRest {
    @GetMapping({"/api/v1/user-service"})
    public String goHome(){
        return "Hello word";
    }
    @GetMapping({"/oauth2/huan"})
    public String demo02(){
        return "Hello word 02";
    }

    @GetMapping({"/huan/authentication"})
    public String demo03(){
        return "Hello word HUAN";
    }

    @GetMapping({"/exception/denied"})
    public String demo04(){
        return "Hello word HUAN";
    }

    @GetMapping("/api/v1/user-service/login/oauth2/code/github")
    public ResponseEntity<String> login(@RequestParam("code") String code,
                                                   @RequestParam("state") String state) {
        // Lấy access token từ request
        log.info(code);

        // Trả về access token
        return ResponseEntity.ok(code);
    }
}
