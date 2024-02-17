package com.library.binhson.userservice.rest;

import com.library.binhson.userservice.dto.*;
import com.library.binhson.userservice.service.IMyProfileService;
import com.library.binhson.userservice.service.IUserService;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/user-service/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final IMyProfileService profileService;
    @GetMapping({"/",""})
    public ResponseEntity<?> getPage(@RequestBody(required = false) PageRequest pageRequest) throws Exception {
        if(Objects.isNull(pageRequest) || pageRequest.page()==0 || pageRequest.size()==0)
            pageRequest=PageRequest.builder()
                    .page(1)
                    .size(10)
                    .build();
        if(pageRequest.page()<=-1 || pageRequest.size()<=-1)
            throw new BadRequestException("Page and size number must be more highly than zero.");
        try {
            List<UserDto> userDtos = userService.getAll();
            log.info(userDtos.size()+"");
            UserPageDto page = new UserPageDto(userDtos, pageRequest.size(), pageRequest.page());
            return ResponseEntity.ok(page.getSet());
        }catch (Exception ex){
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id", required = true) String id){
        List<UserDto> userDtos = userService.getAll();
        return ResponseEntity.ok(userDtos.stream().filter(userDto ->  userDto.getIdentityLibraryCode().equals(id.trim())).findFirst().get());
    }

    @GetMapping("")
    @Secured("authenticated")
    public ResponseEntity<?> getMyInfo(){
        var profile =profileService.getMyInfo();
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("")
    @Secured("authenticated")
    @CacheEvict(value = "all_users", allEntries = true)
    public ResponseEntity<?> updatePersonalInfo(@RequestBody UpdateProfileRequest updateProfile){
        var updatedUser=profileService.update(updateProfile);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/avatar")
    @CacheEvict(value = "all_users", allEntries = true)
    @Secured("authenticated")
    public void setAvatar(@RequestParam("avatar") MultipartFile avatarFile ){
        profileService.setAvatar(avatarFile);
    }


    @PostMapping("replace-email")
    @Secured("authenticated")
    @CacheEvict(value = "all_users", allEntries = true)
    public void changeEmail(@RequestBody ChangeEmail changeEmail){
        if(Objects.isNull(changeEmail.newEmail()) || changeEmail.newEmail().trim().isEmpty())
            throw new BadRequestException("Change email must be fill.");
        profileService.changeMail(changeEmail);
    }














}
