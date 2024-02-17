package com.library.binhson.userservice.dto;

import com.library.binhson.userservice.entity.Permission;
import com.library.binhson.userservice.entity.Role;
import com.library.binhson.userservice.entity.Status;
import com.library.binhson.userservice.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequestDto {
    private Long id;
    private String userId;
    private Role role;
    private Status status;
    private  Date date;
    private Permission requestPermission;
}
