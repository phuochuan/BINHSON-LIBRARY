package com.library.binhson.userservice.dto;

public record RegistrationRequest(String username,
                                  String email,
                                  String password,
                                  String firstName ,
                                  String lastName) {
}
