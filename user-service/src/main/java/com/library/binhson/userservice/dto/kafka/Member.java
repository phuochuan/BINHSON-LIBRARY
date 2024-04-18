package com.library.binhson.userservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

public class Member extends Person{
    public Member(String id, String username, Date dateOfBirth) {
        super(id, username, dateOfBirth);
    }

    public Member() {
    }
}
