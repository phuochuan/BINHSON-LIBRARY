package com.library.binhson.documentservice.dto.request;

import java.util.Date;

public record RequestAuthorDto(
     String name,
     Date date_of_birth,
     Date date_of_die,
     String biography
){}
