package com.library.binhson.documentservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Data

@DiscriminatorValue("Librarian")
public class Librarian  extends Person{
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "person_id"))
    private List<String> roles;
    public Librarian(String id, String username, List<String> roles) {
        super(id, username);
        this.roles = roles;
    }
}
