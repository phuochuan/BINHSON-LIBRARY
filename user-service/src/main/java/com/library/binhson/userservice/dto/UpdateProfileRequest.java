package com.library.binhson.userservice.dto;

public record UpdateProfileRequest( String phone,
         String username,
         String fistName,
         String lastName,
         String address) {

}
