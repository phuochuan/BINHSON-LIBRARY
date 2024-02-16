package com.library.binhson.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private String identityLibraryCode;
    private String email;
    private String phone;
    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private  byte[] avatar;
    private boolean isNonLocked;
    private String biography;
    private Date dateOfBirth;
    private Date dateOfAccountSignUp;
    private String NoDotCitizenIdentityCardId;
    private String placeOfOrigin;
    private String placeOfResidence;
    private String personalIdentification;


}
