package com.library.binhson.userservice.rest;

import com.library.binhson.userservice.dto.PageRequest;
import com.library.binhson.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user-service/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
//    public
    private final IUserService userService;

    @GetMapping()
    public ResponseEntity<?> get(@RequestBody PageRequest pageRequest){
        return null;
    }

    @GetMapping("/my-info")
    @Secured("authenticated")
    public ResponseEntity<?> getMyInfo(){
        return null;
    }

    @PostMapping("/my-info")
    @Secured("authenticated")
    public ResponseEntity<?> updatePersonalInfo(){
        return null;
    }


}
