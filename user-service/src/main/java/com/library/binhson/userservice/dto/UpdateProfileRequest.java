package com.library.binhson.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UpdateProfileRequest{
    public String phone;
    public String fistName;
    public String lastName;
    public String address;
    public Date dateOfBirth;
    public String biography;

}
