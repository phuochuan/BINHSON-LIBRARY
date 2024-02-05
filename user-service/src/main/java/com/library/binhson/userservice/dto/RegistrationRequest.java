package com.library.binhson.userservice.dto;

import lombok.Data;

import java.util.Date;

public record RegistrationRequest(String username,
                                  String email,
                                  String password,
                                  String firstName ,
                                  String lastName,
                                  Date dateOfBirth
                                  ) {
}
