package com.library.binhson.userservice.rest;

import com.library.binhson.userservice.dto.*;
import com.library.binhson.userservice.service.IUserService;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user-service/accounts")
@AllArgsConstructor
@Slf4j
public class AccountManagementController
{
    private final IUserService userService;
    //CRUD
    @GetMapping()
    public ResponseEntity<?> getPage(@RequestBody(required = false) PageRequest pageRequest) throws Exception {
        if(Objects.isNull(pageRequest) || pageRequest.page()==0 || pageRequest.size()==0)
            pageRequest=PageRequest.builder()
                    .page(1)
                    .size(10)
                    .build();
        if(pageRequest.page()<=-1 || pageRequest.size()<=-1)
            throw new BadRequestException("Page and size number must be more highly than zero.");
        try {
            List<UserDto> userDtos = userService.getDetailsAll();
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
        List<UserDto> userDtos = userService.getDetailsAll();
        return ResponseEntity.ok(userDtos.stream().filter(userDto ->  userDto.getIdentityLibraryCode().equals(id.trim())).findFirst().get());
    }

    @PostMapping("/creation")
    @CacheEvict(value = "all_users", allEntries = true)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrations(@RequestBody AccountRC account){
        var user=userService.createUser(account);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/nullification/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void disable(@PathVariable(value = "id", required = true) String id){
        userService.disableUser(id);
    }

    @PatchMapping("/{id}")
    @CacheEvict(value = "all_users", allEntries = true)
    public ResponseEntity<?> updatePersonalInfo(@RequestBody UpdateProfileRequest updateProfile,
                                                @PathVariable(value = "id", required = true) String userId
    ){
        if(Objects.isNull(updateProfile))
            return ResponseEntity.status(403).body("\"message\": \"You haven't filled updating information yet. \"");
        var user=userService.update(userId,updateProfile);
        return ResponseEntity.ok(user);
    }



}
