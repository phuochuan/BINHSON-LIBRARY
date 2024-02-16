package com.library.binhson.userservice.dto;

import javax.management.relation.Role;
import java.util.Date;

public record AccountRC (String username,
                         String email,
                         String password,
                         String firstName ,
                         String lastName,
                         Date dateOfBirth,
                         Role role){
}
