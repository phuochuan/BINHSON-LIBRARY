package com.library.binhson.userservice.rest;

import com.library.binhson.userservice.dto.PageRequest;
import com.library.binhson.userservice.dto.PersonalInfoDto;
import com.library.binhson.userservice.dto.UpdateProfileRequest;
import com.library.binhson.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/user-service/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
//    public
//    private final IUserService userService;

    @GetMapping({"","/"})
    public ResponseEntity<?> get(@RequestBody(required = false) PageRequest pageRequest){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id", required = true) String id){
        return null;
    }


    //my-info ... MY
    @GetMapping("/my-info")
    @Secured("authenticated")
    public ResponseEntity<?> getMyInfo(){
        return null;
    }

    //Set profile
    @PostMapping("/my-info")
    @Secured("authenticated")
    public ResponseEntity<?> setMyProfile(@RequestBody PersonalInfoDto personalInfoDto){
        return null;
    }

    @PatchMapping("/my-info")
    @Secured("authenticated")
    public ResponseEntity<?> updatePersonalInfo(@RequestBody UpdateProfileRequest updateProfile){
        return null;
    }

    @PostMapping("/avatar")
    @Secured("authenticated")
    public ResponseEntity<?> setAvatar(@RequestParam("avatar") MultipartFile avatarFile ){
        return null;
    }


}
