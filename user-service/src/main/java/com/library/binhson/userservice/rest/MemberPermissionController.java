package com.library.binhson.userservice.rest;

import com.library.binhson.userservice.dto.BaseResponse;
import com.library.binhson.userservice.dto.BorrowPermissionRequest;
import com.library.binhson.userservice.dto.GetRequest;
import com.library.binhson.userservice.dto.PermissionRequestDto;
import com.library.binhson.userservice.service.IMemberService;
import com.library.binhson.userservice.service.IPermissionService;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user-service/permission/member")
@AllArgsConstructor
public class MemberPermissionController {
    //table :  member permission request
    //
    private final IPermissionService permissionService;
    @PostMapping("borrow_permission")
    @Secured("hasRole('MEMBER')")
    ResponseEntity<?> requestBorrowPermission(@RequestBody BorrowPermissionRequest permissionRequest){
        if(Objects.isNull(permissionRequest))
            throw new BadRequestException();
        permissionService.requestBorrowPermission(permissionRequest);
        return ResponseEntity.ok(BaseResponse.builder().message("Please wait on various phut.").build());
    }

    @GetMapping({"/", ""})
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> getRequests(@RequestBody(required = false)GetRequest request){
        List<PermissionRequestDto> permissionRequestDtos;
        if(Objects.isNull(request)|| Objects.isNull(request.page()))
            permissionRequestDtos=permissionService.getPermission(1,10);
        else
            permissionRequestDtos=permissionService.getPermission(request.page(), request.size());
        return ResponseEntity.ok(permissionRequestDtos);
    }

    @GetMapping("/user/{userId}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> getRequests(@PathVariable("userId") String userId){
        List<PermissionRequestDto> permissionRequestDtos=permissionService.getPermission(userId);
        return ResponseEntity.ok(permissionRequestDtos);
    }

    @PostMapping("/accept/{id}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> accept(@PathVariable("id")Long id){
        permissionService.accept(id);
        return ResponseEntity.ok(BaseResponse.builder().message("Success").build());
    }
    @PostMapping("/allow/borrow/{userId}")
    @Secured("hasRoles('LIBRARIAN', 'ADMIN')")
    ResponseEntity<?> gantPermission(@PathVariable("userId") String userId){
        permissionService.gantBorrowPermission(userId);
        return ResponseEntity.ok(BaseResponse.builder().message("Success").build());
    }


}
