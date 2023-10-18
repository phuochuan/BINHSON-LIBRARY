package com.library.binhson.userservice.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-service")
public class demoRest {
    @RequestMapping({"","/"})
    public String goHome(){
        return "Hello word";
    }
}
