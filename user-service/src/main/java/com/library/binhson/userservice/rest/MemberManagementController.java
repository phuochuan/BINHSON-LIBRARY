package com.library.binhson.userservice.rest;

import com.library.binhson.userservice.dto.AccountRC;
import com.library.binhson.userservice.dto.BaseResponse;
import com.library.binhson.userservice.dto.GetRequest;
import com.library.binhson.userservice.dto.UpdateProfileRequest;
import com.library.binhson.userservice.entity.Role;
import com.library.binhson.userservice.service.IMemberService;
import com.library.binhson.userservice.service.IUserService;
import com.library.binhson.userservice.service.third_party_system.keycloak.MembersKeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user-service/members")
@RequiredArgsConstructor
public class MemberManagementController {
    private final IMemberService memberService;
    private final IUserService userService;
    private final MembersKeycloakService membersKeycloakService;

    @GetMapping("")
    ResponseEntity<?> getMembers(@RequestBody(required = false) GetRequest getMemberRequest ){
        Object members=null;
        if(Objects.isNull(getMemberRequest) || Objects.isNull(getMemberRequest.page()))
             members= memberService.getMember(1,10);
        //page start from index 01.
        else {

            members= memberService.getMember(getMemberRequest.page(),getMemberRequest.size());
        }
        return ResponseEntity.ok(members);
    }
    @GetMapping("/{id}")
    ResponseEntity<?> getMemberById(@PathVariable("id") String memberId){
        var member=memberService.findById(memberId);
        return ResponseEntity.ok(member);
    }
    //Permission request

    @PostMapping("/creation")
    @CacheEvict(value = "all_users", allEntries = true)
    public ResponseEntity<?> registrations(@RequestBody AccountRC account){
        AccountRC accountRC=new AccountRC(account.username(), account.email(), account.password(), account.firstName(), account.lastName(), account.date_of_birth(), Role.MEMBER);
        var user=userService.createUser(account);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    @CacheEvict(value = "all_users", allEntries = true)
    public ResponseEntity<?> updatePersonalInfo(@RequestBody UpdateProfileRequest updateProfile,
                                                @PathVariable(value = "id", required = true) String userId
    ){
        if(Objects.nonNull(updateProfile))
            if(membersKeycloakService.isMember(userId)) {
                var user=userService.update(userId, updateProfile);
                return ResponseEntity.ok(BaseResponse.builder().message("Update Successfully").build());
            }
        return ResponseEntity.status(403).body("\"message\": \"You haven't filled any updating information yet. \"");
    }

}
