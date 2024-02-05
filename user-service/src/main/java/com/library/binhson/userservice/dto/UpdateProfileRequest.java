package com.library.binhson.userservice.dto;

import java.util.Date;

public record UpdateProfileRequest(String phone,
                                   String fistName,
                                   String lastName,
                                   String address,
                                   Date dateOfBirth,
                                   String biography
                                   ) {

}
