package com.library.binhson.userservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class Member extends Person{
    public Member(String id, String username) {
        super(id, username);
    }
}
