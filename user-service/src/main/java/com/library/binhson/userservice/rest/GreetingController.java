package com.library.binhson.userservice.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-service/greeting")
public class GreetingController {
    @GetMapping()
    @PreAuthorize("hasRole('USER01')")
    public String greeting(){
        return "hello world";
    }
}
