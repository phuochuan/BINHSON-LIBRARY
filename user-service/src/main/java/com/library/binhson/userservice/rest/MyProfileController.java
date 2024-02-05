package com.library.binhson.userservice.rest;

import com.library.binhson.userservice.dto.ChangeEmail;
import com.library.binhson.userservice.dto.UserDto;
import com.library.binhson.userservice.dto.UpdateProfileRequest;
import com.library.binhson.userservice.service.IMyProfileService;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user-service/user")
@AllArgsConstructor
public class MyProfileController {
    private final IMyProfileService profileService;
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
