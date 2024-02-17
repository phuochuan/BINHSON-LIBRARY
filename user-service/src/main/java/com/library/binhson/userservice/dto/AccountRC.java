package com.library.binhson.userservice.dto;

import com.library.binhson.userservice.entity.Role;

import java.util.Date;

public record AccountRC (String username,
                         String email,
                         String password,
                         String firstName ,
                         String lastName,
                         Date date_of_birth,
                         Role role){
}
