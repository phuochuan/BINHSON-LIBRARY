package com.library.binhson.documentservice.dto.Request;

import java.util.Date;

public record RequestAuthorDto(
     String name,
     Date date_of_birth,
     Date date_of_die,
     String biography
){}
