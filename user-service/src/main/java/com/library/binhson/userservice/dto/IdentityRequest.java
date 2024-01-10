package com.library.binhson.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public record IdentityRequest(String cccd, Date dateOfProvide, String govOfProvide) {
}
