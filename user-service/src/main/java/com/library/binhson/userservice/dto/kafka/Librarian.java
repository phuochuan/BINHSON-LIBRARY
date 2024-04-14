package com.library.binhson.userservice.dto.kafka;

import com.library.binhson.userservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data

public class Librarian extends Person{
    private Role role;
    public Librarian(String id, String username, Role role) {
        super(id, username);
        this.role = role;
    }

    public Librarian(Role role) {
        this.role = role;
    }
}
