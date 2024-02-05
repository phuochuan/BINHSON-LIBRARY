package com.library.binhson.userservice.dto;

import lombok.Builder;

@Builder
public record PageRequest (int page,int size){
}
