package com.library.binhson.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoDto {
    private String identityLibraryCode;
    private String email;
    private String phone;
    private String username;
    private String fistName;
    private String lastName;
    private String address;
}
